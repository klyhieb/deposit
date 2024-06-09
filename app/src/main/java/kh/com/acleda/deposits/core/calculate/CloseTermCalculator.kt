package kh.com.acleda.deposits.core.calculate

import android.os.Build
import androidx.annotation.RequiresApi
import kh.com.acleda.deposits.core.getYearAsDouble
import kh.com.acleda.deposits.core.percentageToDouble
import kh.com.acleda.deposits.modules.home.domain.model.TermType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class CloseTermCalculator(private val termType: TermType, private val termMonths: Int) {

    private fun getCreditMonthsByTermType(): Int {
        return when(termType) {
            TermType.HI_GROWTH -> termMonths
            TermType.HI_INCOME -> 1
            TermType.LONG_TERM -> 3
            TermType.DEFAULT -> 0 // this case should not represents
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getStartDate(): LocalDate = LocalDate.now() // we take current date as starting date

    @RequiresApi(Build.VERSION_CODES.O)
    fun depositDays(today: LocalDate = getStartDate(), effectiveDateStr: String): Long {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val effectiveDate = LocalDate.parse(effectiveDateStr, formatter)
        return ChronoUnit.DAYS.between(effectiveDate,today)
    }

    /* cal. interest amount for the given term */
    fun totalInterestAmount(principalAmount: Double, annualRate: Double): Double {
        val yearlyInterest = principalAmount * annualRate.percentageToDouble()
        val creditMonth = getCreditMonthsByTermType()
        return yearlyInterest * creditMonth.getYearAsDouble()
    }

    fun dailyInterest(totalInterestAmount: Double): Double {
        val totalDays = getCreditMonthsByTermType() * 30 // 30 days for 1 months
        return totalInterestAmount / totalDays
    }

    fun receivedInterest(dailyInterest: Double, depositDays: Long): Double {
        return dailyInterest * depositDays
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val cal = CloseTermCalculator(TermType.HI_GROWTH, 5)
    val numberdays = cal.depositDays(effectiveDateStr = "2024-05-11")
    val totalInterestAmount = cal.totalInterestAmount(100.0, 3.40)
    val dailyInterest = cal.dailyInterest(totalInterestAmount)
    val recievedInterest = cal.receivedInterest(dailyInterest, numberdays)

    println(numberdays)
    println(totalInterestAmount)
    println(dailyInterest)
    println(recievedInterest)
}