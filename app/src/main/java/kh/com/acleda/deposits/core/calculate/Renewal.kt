package kh.com.acleda.deposits.core.calculate

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.pow

class OpenTermCalculator {

    // Function to calculate new maturity date
    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateNewMaturityDate(originalDate: String, renewalCount: Int, termMonths: Long): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val originalMaturityDate = LocalDate.parse(originalDate, formatter)
        val newMaturityDate = originalMaturityDate.plusMonths(termMonths * renewalCount)
        return newMaturityDate.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDateFormat(inputDate: String): String {
        // Define the input and output date formats
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")

        // Parse the input date string to a LocalDate object
        val date = LocalDate.parse(inputDate, inputFormatter)

        // Format the LocalDate object to the desired output format
        return date.format(outputFormatter)
    }

    // Function to calculate the amount after renewals using compound interest
    fun calculateAmountAfterRenewals(principal: Double, annualRate: Double, renewalCount: Int, termYears: Double): Double {
        val ratePerTerm = annualRate * termYears
        return principal * (1 + ratePerTerm).pow(renewalCount)
    }

    // Function to calculate total interest earned after renewals
    fun calculateTotalInterest(principal: Double, amountAfterRenewals: Double): Double {
        return amountAfterRenewals - principal
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