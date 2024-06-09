package kh.com.acleda.deposits.core.calculate

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class StopRenewalCalculator {
    /* calculate new maturity date by deduct renewal times to zero (0) */
    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateNewMaturityDate(originalDate: String, renewalCount: Int, termMonths: Long): String {
        val originalMaturityDate = LocalDate.parse(originalDate)
        val totalMonths = termMonths * (renewalCount + 1)  // 1 represent the default term period
        return originalMaturityDate.minusMonths(totalMonths).toString()
    }
}