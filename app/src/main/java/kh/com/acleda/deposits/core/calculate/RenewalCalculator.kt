package kh.com.acleda.deposits.core.calculate

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.pow

class RenewalCalculator {
    // Function to calculate new maturity date
    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateNewMaturityDate(originalDate: String, renewalCount: Int, termMonths: Long): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val originalMaturityDate = LocalDate.parse(originalDate, formatter)
        val newMaturityDate = originalMaturityDate.plusMonths(termMonths * renewalCount)
        return newMaturityDate.format(formatter)
    }

    // Function to calculate the amount after renewals using compound interest
    fun calculateAmountAfterRenewals(principal: Double, annualRate: Float, renewalTime: Int, termMonths: Int): Double {
        val termInYears: Double = termMonths / 12.0  // 12 is months in year
        val ratePerTerm = annualRate * termInYears
        return principal * (1 + ratePerTerm).pow(renewalTime)
    }

    // Function to calculate total interest earned after renewals
    fun calculateTotalInterest(principal: Double, amountAfterRenewals: Double): Double {
        return amountAfterRenewals - principal
    }

    // Function to calculate tax amount
    fun calculateTax(totalInterest: Double, taxRate: Double): Double {
        val texInDecimal = taxRate / 100
        return totalInterest * texInDecimal
    }

    // Function to calculate net monthly interest
    fun calculateNetMonthlyInterest(netInterestAfterTax: Double, totalMonths: Int): Double {
        return netInterestAfterTax / totalMonths
    }

    // Function to calculate total amount to receive at final maturity
    fun calculateTotalToReceive(principal: Double, netInterestAfterTax: Double): Double {
        return principal + netInterestAfterTax
    }
}