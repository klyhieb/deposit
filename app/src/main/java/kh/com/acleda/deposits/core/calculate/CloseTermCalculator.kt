package kh.com.acleda.deposits.core.calculate

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class CloseTermCalculator {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getStartDate(): LocalDate = LocalDate.now() // we take current date as starting date

    @RequiresApi(Build.VERSION_CODES.O)
    fun depositDays(today: LocalDate = getStartDate(), effectiveDateStr: String): Long {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val effectiveDate = LocalDate.parse(effectiveDateStr, formatter)
        return ChronoUnit.DAYS.between(effectiveDate,today)
    }

    /* cal. interest amount for the given term */
    fun interest(principalAmount: Double, annualRate: Double, numberOfDays: Long): Double {
        return (principalAmount * annualRate * numberOfDays) / (YEAR_NUMBER * PERCENTAGE_NUMBER)
    }

    /* cal. tax on interest */
    fun tax(interestAmount: Double, taxRate: Double): Double {
        return interestAmount * (taxRate / PERCENTAGE_NUMBER)
    }

    /* cal. net interest amount, amount after tax */
    fun netInterest(interestAmount: Double, taxAmount: Double): Double {
        return interestAmount - taxAmount
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val cal = CloseTermCalculator()
    val depositDays = cal.depositDays(effectiveDateStr = "2024-02-14")
    val interest = cal.interest(principalAmount = 100.0, annualRate = 2.35, numberOfDays = depositDays)
    val tax = cal.tax(interestAmount = interest, taxRate = 6.0)
    val netInterest = cal.netInterest(interestAmount = interest, taxAmount = tax)

    println("Deposit days: $depositDays")
    println("Interest: $interest")
    println("Tax: $tax")
    println("Net Interest: $netInterest")
}