package kh.com.acleda.deposits.core.calculate

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RenewalCalculator {
    // Function to calculate new maturity date
    @RequiresApi(Build.VERSION_CODES.O)
    fun newMaturityDate(originalDate: String, renewalTime: Int, termMonths: Long): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val originalMaturityDate = LocalDate.parse(originalDate, formatter)
        val newMaturityDate = originalMaturityDate.plusMonths(termMonths * renewalTime)
        return newMaturityDate.format(formatter)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val originalDate = "2025-01-08"
    val renewalCount = 2
    val termMonths = 8L

    val newMaturityDate = RenewalCalculator().newMaturityDate(originalDate, renewalCount, termMonths)
    println(newMaturityDate)
}