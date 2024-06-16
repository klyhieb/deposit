package kh.com.acleda.deposits.core.calculate

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class StopRenewalCalculator {
    /* Calculate new maturity date by deducting renewal times to zero (0) */
    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateNewMaturityDate(originalMaturityDate: String, renewalCount: Int, termMonths: Long): String {
        val originalMaturityDate = LocalDate.parse(originalMaturityDate)
        val totalMonthsToDeduct = termMonths * (renewalCount)
        return originalMaturityDate.minusMonths(totalMonthsToDeduct).toString()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val originalDate = "2024-12-09"
    val renewalCount = 2
    val termMonths = 2L

    val newMaturityDate = StopRenewalCalculator().calculateNewMaturityDate(originalDate, renewalCount, termMonths)
    println(newMaturityDate)

}