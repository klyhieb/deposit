package kh.com.acleda.deposits.core.calculate

import android.os.Build
import androidx.annotation.RequiresApi
import kh.com.acleda.deposits.modules.home.domain.model.TermType
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * Base formula:
 * Total Interest = (Principle * (interest rate/100) * number of day) /365
 */

const val PERCENTAGE_NUMBER = 100
const val YEAR_NUMBER = 365

data class TotalReceivedModel(
    val interestWithTax: Double = 0.0,
    val taxAmount: Double = 0.0,
    val netInterest: Double = 0.0,
    val totalToReceive: Double = 0.0
)

class OpenTermCalculator(private val termType: TermType, private val termMonths: Int) {
    private var invalidAmount: Boolean = true

    /* cal. actual total months for Term */
    private fun actualRenewalTime(renewalTime: Int = 0) = renewalTime + 1 // 1 represents the default term period

    /* This checks amount by currency which allow to TD or not
    *  - Return true if the amount is invalid (not allowed)
    *  - otherwise return false
    */
    private fun invalidAmount(principalAmount: Double, ccy: CCY): Boolean {
        when (ccy) {
            CCY.RIEL -> if (principalAmount < 400000.0) return true
            CCY.DOLLAR -> if (principalAmount < 100.0) return true
            CCY.DEFAULT -> return true
        }
        return false
    }

    /* init invalid amount variable */
    fun checkInvalidAmount(principalAmount: Double, ccy: CCY) {
        invalidAmount = invalidAmount(principalAmount, ccy)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun numberOfDays(startDate: LocalDate = getStartDate(), endDate: LocalDate): Long {
        return ChronoUnit.DAYS.between(startDate, endDate)
    }

    /**
     * in ACLEDA
     * ⚡️ Total Interest base on deposit Type: ⚡️
     * Hi-Growth: Total will base interest in Term months
     * Hi-Income: Total will fix 1 Month
     * Long Term: Total will fix 3 Months
     */
    fun getCreditMonthsByTermType(): Int {
        return when(termType) {
            TermType.HI_GROWTH -> termMonths
            TermType.HI_INCOME -> 1
            TermType.LONG_TERM -> 3
            TermType.DEFAULT -> 0 // this case should not represent
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getStartDate(): LocalDate = LocalDate.now() // we take the current date as the starting date

    @RequiresApi(Build.VERSION_CODES.O)
    fun maturityDate(startDate: LocalDate = getStartDate()): LocalDate {
        val creditMonth = getCreditMonthsByTermType()
        return startDate.plusMonths(creditMonth.toLong())
    }

    /* cal. the final maturity date */
    @RequiresApi(Build.VERSION_CODES.O)
    fun finalMaturityDate(startDate: LocalDate = getStartDate(), renewalTime: Int): LocalDate {
        val totalMonths = termMonths * actualRenewalTime(renewalTime)
        return startDate.plusMonths(totalMonths.toLong())
    }

    /* cal. interest amount for the given term */
    fun interest(principalAmount: Double, annualRate: Double, numberOfDays: Long): Double {
        if (invalidAmount) return 0.0

        return (principalAmount * (annualRate / PERCENTAGE_NUMBER) * numberOfDays) / YEAR_NUMBER
    }

    /* cal. tax on interest */
    fun tax(interestAmount: Double, taxRate: Double): Double {
        if (invalidAmount) return 0.0

        return interestAmount * (taxRate / PERCENTAGE_NUMBER)
    }

    /* cal. net interest amount, amount after tax */
    fun netInterest(interestAmount: Double, taxAmount: Double): Double {
        if (invalidAmount) return 0.0

        return interestAmount - taxAmount
    }

    /* cal. total to receive */
    fun totalToReceive(principalAmount: Double, netInterest: Double): Double {
        return principalAmount + netInterest
    }

    /* check special case */
    private fun isSpecialCase(renewalOption: String): Boolean = termType == TermType.HI_GROWTH && renewalOption == "Principal & Interest"

    /* cal. total to received for special case */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun totalReceivedSpecialCase(initialPrincipal: Double, renewalTimes: Int, annualRate: Double, taxRate: Double): TotalReceivedModel {
        var startDate = getStartDate()
        var principal = initialPrincipal
        var interestWithTax = 0.0
        var taxAmount = 0.0
        var netInterestAmount = 0.0

        for (i in 1..actualRenewalTime(renewalTimes)) {
            // maturity date & number of days of the current term
            val maturityDate = maturityDate(startDate = startDate)
            val numberOfDays = numberOfDays(startDate = startDate, endDate = maturityDate)

            // interest, tax, & net interest amounts
            val interest = interest(principalAmount = principal, annualRate = annualRate, numberOfDays = numberOfDays)
            val tax = tax(interestAmount = interest, taxRate = taxRate)
            val netInterest = netInterest(interestAmount = interest, taxAmount = tax)

            // for return model result
            interestWithTax += interest
            taxAmount += tax
            netInterestAmount += netInterest

            // update the principal for the next term
            principal += netInterest

            // Update the start date for the next term
            startDate = maturityDate
        }

        // return final total to received at final maturity date
        return TotalReceivedModel(
            interestWithTax = interestWithTax,
            taxAmount = taxAmount,
            netInterest = netInterestAmount,
            totalToReceive = principal
        )
    }

    /* cal. total to received for special case */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun totalReceivedNormalCase(initialPrincipal: Double, renewalTimes: Int, annualRate: Double, taxRate: Double): TotalReceivedModel {
        val startDate = getStartDate()

        val finalMaturityDate = finalMaturityDate(startDate, renewalTime = renewalTimes)
        val numberOfDays = numberOfDays(startDate = startDate, endDate = finalMaturityDate)
        val interestWithTax = interest(principalAmount = initialPrincipal, annualRate = annualRate, numberOfDays = numberOfDays)
        val taxAmount = tax(interestAmount = interestWithTax, taxRate = taxRate)
        val netInterestAmount = netInterest(interestAmount = interestWithTax, taxAmount = taxAmount)
        val totalToReceive = totalToReceive(principalAmount = initialPrincipal, netInterest = netInterestAmount)

        return TotalReceivedModel(
            interestWithTax = interestWithTax,
            taxAmount = taxAmount,
            netInterest = netInterestAmount,
            totalToReceive = totalToReceive
        )
    }

    /* cal. final total interest of renewal with principal and interest */
    @RequiresApi(Build.VERSION_CODES.O)
    fun finalPrincipal(initialPrincipal: Double, renewalOption: String, renewalTimes: Int, annualRate: Double, taxRate: Double): TotalReceivedModel {
        if (isSpecialCase(renewalOption)) {
            return totalReceivedSpecialCase(
                initialPrincipal = initialPrincipal,
                renewalTimes = renewalTimes,
                annualRate = annualRate,
                taxRate = taxRate
            )
        }

        return totalReceivedNormalCase(
            initialPrincipal = initialPrincipal,
            renewalTimes = renewalTimes,
            annualRate = annualRate,
            taxRate = taxRate
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    // Given data
    val depositTenor = 4 // in months
    val renewalTime = 2
    val renewalOption = "Principal & Interesteee"
    val depositAmount = 100.0 // 100 USD
    val annualRate = 2.35
    val taxRate = 6.00
    val termType = TermType.HI_GROWTH
    val startDateStr = "2024-06-16"
    val startDate = LocalDate.parse(startDateStr)

    // Calculations
    val calculator = OpenTermCalculator(termType = termType, termMonths = depositTenor)
    calculator.checkInvalidAmount(principalAmount = depositAmount, ccy = CCY.DOLLAR)

    val maturityDate = calculator.maturityDate(startDate)
    val firstNumberOfDays = calculator.numberOfDays(startDate = startDate, endDate = maturityDate)
    val finalMaturityDate = calculator.finalMaturityDate(startDate, renewalTime = renewalTime)
    val finalNumberOfDays = calculator.numberOfDays(startDate = startDate, endDate = finalMaturityDate)

    // credit month
    val interestAmountAtCreditMonth = calculator.interest(principalAmount = depositAmount, annualRate = annualRate, numberOfDays = firstNumberOfDays)
    val taxAmountAtCreditMonth = calculator.tax(interestAmount = interestAmountAtCreditMonth, taxRate = taxRate)
    val netInterestAtCreditMonth = calculator.netInterest(interestAmount = interestAmountAtCreditMonth, taxAmount = taxAmountAtCreditMonth)
    val totalReceivedAtAtCreditMonth = calculator.totalToReceive(principalAmount = depositAmount, netInterest = netInterestAtCreditMonth)

    // final maturity
    val totalInterestAmount = calculator.interest(principalAmount = depositAmount, annualRate = annualRate, numberOfDays = finalNumberOfDays)
    val totalTaxAmount = calculator.tax(interestAmount = totalInterestAmount, taxRate = taxRate)
    val totalNetInterest = calculator.netInterest(interestAmount = totalInterestAmount, taxAmount = totalTaxAmount)
    val totalToReceiveAtFinalMaturity = calculator.totalToReceive(principalAmount = depositAmount, netInterest = totalNetInterest)

    // special case Hi-Growth with Principal & Interest
    val finalToReceived = calculator.finalPrincipal(
        initialPrincipal = depositAmount,
        renewalOption = renewalOption,
        renewalTimes = renewalTime,
        annualRate = annualRate,
        taxRate = taxRate,
    )

    // Print results
    println("Maturity Date: $maturityDate")
    println("Final Maturity Date: $finalMaturityDate")
    println()

    println("Number of days to Maturity: $firstNumberOfDays")
    println("Number of days to Final Maturity: $finalNumberOfDays")
    println()

    println("Interest: %.2f USD".format(interestAmountAtCreditMonth))
    println("Tax Amount: %.2f USD".format(taxAmountAtCreditMonth))
    println("Net Interest: %.2f USD".format(netInterestAtCreditMonth))
    println("Total Received At Maturity: %.2f USD".format(totalReceivedAtAtCreditMonth))
    println()

    println("Total Interest: %.2f USD".format(totalInterestAmount))
    println("Total Tax Amount: %.2f USD".format(totalTaxAmount))
    println("Total Net Interest: %.2f USD".format(totalNetInterest))
    println("Total Received At Final Maturity: %.2f USD".format(totalToReceiveAtFinalMaturity))
    println("Total Received At Final Maturity Special Case: $finalToReceived")
}
