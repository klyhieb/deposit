package kh.com.acleda.deposits.core.calculate

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class StopRenewalCalculator {
    /* Calculate new maturity date by deducting renewal times to zero (0) */
    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateNewMaturityDate(originalMaturityDateStr: String, renewalCount: Int, termMonths: Long): String {
        val originalMaturityDate = LocalDate.parse(originalMaturityDateStr)
        val totalMonthsToDeduct = termMonths * renewalCount
        return originalMaturityDate.minusMonths(totalMonthsToDeduct).toString()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val originalDate = "2025-01-08"
    val renewalCount = 0
    val termMonths = 2L

    val newMaturityDate = StopRenewalCalculator().calculateNewMaturityDate(originalDate, renewalCount, termMonths)
    println(newMaturityDate)

}