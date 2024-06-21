package kh.com.acleda.deposits.core.calculate

import android.os.Build
import androidx.annotation.RequiresApi
import kh.com.acleda.deposits.core.roundDoubleAmount
import kh.com.acleda.deposits.modules.home.domain.model.TermType
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * Base formula:
 * Total Interest = (principle * (interest_rate/100) * number_of_day) /365
 * or             = (principle * interest_rate * number_of_day) / (365 * 100)
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

        return (principalAmount * annualRate * numberOfDays) / (YEAR_NUMBER * PERCENTAGE_NUMBER)
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
    private fun totalReceivedStandardCase(initialPrincipal: Double, renewalTimes: Int, annualRate: Double, taxRate: Double): TotalReceivedModel {
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
        if (invalidAmount) return TotalReceivedModel()

        if (isSpecialCase(renewalOption)) {
            return totalReceivedSpecialCase(
                initialPrincipal = initialPrincipal,
                renewalTimes = renewalTimes,
                annualRate = annualRate,
                taxRate = taxRate
            )
        }

        return totalReceivedStandardCase(
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
    val depositTenor = 5 // in months
    val renewalTime = 4
    val renewalOption = "Principal & Interest-4444"
    val depositAmount = 400000.0 // 100 USD
    val annualRate = 4.40
    val taxRate = 6.0
    val termType = TermType.HI_GROWTH
    val ccy = CCY.RIEL

    // Calculations
    val calculator = OpenTermCalculator(termType = termType, termMonths = depositTenor)
    calculator.checkInvalidAmount(principalAmount = depositAmount, ccy = ccy)

    val maturityDate = calculator.maturityDate()
    val numberOfDays = calculator.numberOfDays(endDate = maturityDate)
    val finalMaturityDate = calculator.finalMaturityDate(renewalTime = renewalTime)

    // credit month
    val interestAmountAtCreditMonth = calculator.interest(principalAmount = depositAmount, annualRate = annualRate, numberOfDays = numberOfDays)
    val taxAmountAtCreditMonth = calculator.tax(interestAmount = interestAmountAtCreditMonth, taxRate = taxRate)
    val netInterestAtCreditMonth = calculator.netInterest(interestAmount = interestAmountAtCreditMonth, taxAmount = taxAmountAtCreditMonth)
    val totalReceivedAtAtCreditMonth = calculator.totalToReceive(principalAmount = depositAmount, netInterest = netInterestAtCreditMonth)

    // Final Maturity Both Normal and Special case (Hi-Growth with Principal & Interest)
    val finalToReceived = calculator.finalPrincipal(
        initialPrincipal = depositAmount,
        renewalOption = renewalOption,
        renewalTimes = renewalTime,
        annualRate = annualRate,
        taxRate = taxRate,
    )

    // Print results
    println("Maturity date: $maturityDate")
    println("Final maturity date: $finalMaturityDate")
    println()

    println("Interest at credit month: ${roundDoubleAmount(interestAmountAtCreditMonth, ccy)} ${ccy.dec.uppercase()}")
    println("Tax amount at credit month: ${roundDoubleAmount(taxAmountAtCreditMonth, ccy)} ${ccy.dec.uppercase()}")
    println("Net interest at credit month: ${roundDoubleAmount(netInterestAtCreditMonth, ccy)} ${ccy.dec.uppercase()}")
    println("Total received at credit month : ${roundDoubleAmount(totalReceivedAtAtCreditMonth, ccy)} ${ccy.dec.uppercase()}")
    println()

    println("finalToReceived: $finalToReceived")
    println("Net interest at final: ${roundDoubleAmount(finalToReceived.netInterest, ccy)} ${ccy.dec.uppercase()}")
    println("Total received at final: ${roundDoubleAmount(finalToReceived.totalToReceive, ccy)} ${ccy.dec.uppercase()}")
}
