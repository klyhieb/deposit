package kh.com.acleda.deposits.core.calculate

import android.os.Build
import androidx.annotation.RequiresApi
import kh.com.acleda.deposits.core.percentageToDouble
import kh.com.acleda.deposits.core.getYearAsDouble
import kh.com.acleda.deposits.modules.home.domain.model.TermType
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import java.time.LocalDate

class OpenTermCalculator(private val termType: TermType, private val termMonths: Int) {
    private var invalidAmount: Boolean = true

    /* cal. actual total months for Term */
    private fun actualRenewalTime(renewalTime: Int = 0) = renewalTime + 1 // 1 represent the default term period

    fun getCreditMonthsByTermType(): Int {
        return when(termType) {
            TermType.HI_GROWTH -> termMonths
            TermType.HI_INCOME -> 1
            TermType.LONG_TERM -> 3
            TermType.DEFAULT -> 0 // this case should not represents
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getStartDate(): LocalDate = LocalDate.now() // we take current date as starting date

    /* cal. the maturity date */
    @RequiresApi(Build.VERSION_CODES.O)
    fun maturityDate(startDate: LocalDate = getStartDate(), renewalTime: Int): String {
        val totalMonths = termMonths * actualRenewalTime(renewalTime)
        return startDate.plusMonths(totalMonths.toLong()).toString()
    }

    /* cal. interest amount for the given term */
    private fun totalInterestAmount(principalAmount: Double, annualRate: Double): Double {
        if (invalidAmount) return 0.0

        val yearlyInterest = principalAmount * annualRate.percentageToDouble()
        val creditMonth = getCreditMonthsByTermType()
        return yearlyInterest * creditMonth.getYearAsDouble()
    }

    /**
     * in ACLEDA
     * ⚡️ Total Interest base on deposit Type: ⚡️
     * Hi-Growth: Total will base interest by Term months
     * Hi-Income: Total will fix 1 Months
     * Long Term: Total will fix 3 Months
     */
    fun totalInterestAmountByTermType(principalAmount: Double, annualRate: Double): Double {
        if (invalidAmount) return 0.0

        return totalInterestAmount(principalAmount, annualRate)
    }

    /* This check amount by currency which allow to TD or not
    *  - Return true if the amount is invalid (not allow)
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

    /* cal. tax on interest */
    fun taxAmount(totalInterestAmount: Double, taxRate: Double): Double {
        if (invalidAmount) return 0.0

        return totalInterestAmount * taxRate.percentageToDouble()
    }

    /**
     * in ACLEDA
     * ⚡️ Total Interest base on deposit Type: ⚡️
     * Hi-Growth: Total will base interest by Term months
     * Hi-Income: Total will fix 1 Months
     * Long Term: Total will fix 3 Months
     */
    fun netInterestByTermType(totalInterestAmount: Double, taxAmount: Double): Double {
        if (invalidAmount) return 0.0

        return totalInterestAmount - taxAmount
    }

    /**
     * For this we can use like below:
     * - When user no-renewal or renewal with principle
     *      : Total To Received at Maturity =
     *
     * - When user renewal with principle & interest
     *      : Total To Received at First Maturity =
     *      each maturity will received different amount base on
     *      new add interest amount to the principal amount
     */

    fun totalToReceiveAtMaturity(principalAmount: Double, totalInterestAmount: Double, taxRate: Double): Double {
        if (invalidAmount) return 0.0

        val totalReceivedInterestTime = termMonths / getCreditMonthsByTermType()
        val totalInterestAtMaturity = totalInterestAmount * totalReceivedInterestTime
        val taxAmount = taxAmount(totalInterestAtMaturity, taxRate)
        return principalAmount + totalInterestAtMaturity - taxAmount
    }

    /**
     * This can be calculate if user renewal in principal only
     */
    fun totalToReceiveAtFinalMaturity(principalAmount: Double, totalToReceiveAtMaturity: Double, renewalTime: Int): Double {
        if (invalidAmount) return 0.0

        val netMaturityInterest = totalToReceiveAtMaturity - principalAmount // for a maturity time
        return (netMaturityInterest * actualRenewalTime(renewalTime)) + principalAmount
    }

    fun totalToReceiveAtFinalMaturityByType(principalAmount: Double, renewalTime: Int, renewalOption: String, annualRate: Double, taxRate: Double): Double {
        if (invalidAmount) return 0.0

        // only Hi-Growth has "Principal & Interest" renewal option
        if (termType != TermType.HI_GROWTH || renewalOption != "Principal & Interest") {
            val totalInterestAmount = totalInterestAmountByTermType(principalAmount = principalAmount, annualRate = annualRate)
            val totalReceivedAtMaturity = totalToReceiveAtMaturity(principalAmount = principalAmount, totalInterestAmount = totalInterestAmount, taxRate = taxRate)
            val totalToReceiveAtFinalMaturity = totalToReceiveAtFinalMaturity(principalAmount = principalAmount, totalToReceiveAtMaturity = totalReceivedAtMaturity, renewalTime = renewalTime )
            return totalToReceiveAtFinalMaturity
        }

        // This calculate work with term type = Hi-Growth & auto renewal = Principal & Interest
        var finalTotalInterestAmount = 0.0
        var mPrincipalAmount = principalAmount

        var totalInterestAmount: Double
        var taxAmount: Double
        var netInterestAmount: Double

        for (x in 0 until actualRenewalTime(renewalTime)) {
            totalInterestAmount = totalInterestAmountByTermType(principalAmount = mPrincipalAmount, annualRate = annualRate)
            taxAmount = taxAmount(totalInterestAmount = totalInterestAmount, taxRate = taxRate)
            netInterestAmount = netInterestByTermType(totalInterestAmount = totalInterestAmount, taxAmount = taxAmount)
            finalTotalInterestAmount += netInterestAmount
            mPrincipalAmount += netInterestAmount
        }

        return finalTotalInterestAmount + principalAmount
    }

    /* calculate the net interest at the final maturity */
    fun totalNetInterest(principalAmount: Double, totalToReceiveAtFinalMaturity: Double): Double {
        if (invalidAmount) return 0.0

        return totalToReceiveAtFinalMaturity - principalAmount
    }

    /* calculate total interest at the final maturity (without minus tax amount) */
    fun totalInterestWithTax(principalAmount: Double, totalInterestAtFinalMaturityWithTax: Double): Double {
        if (invalidAmount) return 0.0

        return totalInterestAtFinalMaturityWithTax - principalAmount
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    // Given data
    val depositTenor = 5
    val renewalTime = 2
    val depositAmount = 400000.0
    val annualRate = 1.99 // 4.50%  // 0.0475
    val taxRate = 6.00 // 6.00%
    val termtype = TermType.HI_GROWTH
    val renewalOption = "Principal"
    val startDAteStr = "2024-05-25"
    val startDate = LocalDate.parse(startDAteStr)

    // Calculations
    val calculator = OpenTermCalculator(termType = termtype, termMonths = depositTenor)
    val maturityDate = calculator.maturityDate(startDate = startDate, renewalTime = renewalTime)
    val interestAmount = calculator.totalInterestAmountByTermType(principalAmount = depositAmount, annualRate = annualRate)
    val taxAmount = calculator.taxAmount(totalInterestAmount = interestAmount, taxRate = taxRate)
    val netInterest = calculator.netInterestByTermType(totalInterestAmount = interestAmount, taxAmount = taxAmount)
    val totalReceivedAtMaturity = calculator.totalToReceiveAtMaturity(principalAmount = depositAmount, totalInterestAmount = interestAmount, taxRate = taxRate)
    val totalToReceiveAtFinalMaturity = calculator.totalToReceiveAtFinalMaturity(principalAmount = depositAmount, totalToReceiveAtMaturity = totalReceivedAtMaturity, renewalTime = renewalTime )

    val totalToReceiveAtFinalMaturityPrincipalAndInterest = calculator.totalToReceiveAtFinalMaturityByType(
        principalAmount = depositAmount,
        renewalTime = renewalTime,
        renewalOption = renewalOption,
        annualRate = annualRate,
        taxRate = taxRate
    )

    // Print results
    println("Maturity Date: $maturityDate")
    println("Interest Amount by Term Type: %.2f USD".format(interestAmount))
    println("Tax Amount: %.2f USD".format(taxAmount))
    println("Net Interest by Term Type: %.2f USD".format(netInterest))
    println("Total Received At Maturity: %.2f USD".format(totalReceivedAtMaturity))
    println("Total Received At Final Maturity: %.2f USD".format(totalToReceiveAtFinalMaturity))
    println("Total Received At Final Maturity Principal & Interest: %.2f USD".format(totalToReceiveAtFinalMaturityPrincipalAndInterest))
}
