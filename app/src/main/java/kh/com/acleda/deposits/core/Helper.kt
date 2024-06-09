package kh.com.acleda.deposits.core

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import kh.com.acleda.deposits.core.util.ExchangeRate
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun <T> safeConvertAccountBalance(
    model: T,
    ccy: (T) -> CCY,
    amount: (T) -> Float
): Float {
    val exchangeRate = ExchangeRate()

    // we try to safe convert Amount to Dollar in case it CCY.RIEL
    return if (ccy(model) == CCY.RIEL)
        exchangeRate.riel2Dollar(amount(model))
    else
        amount(model)
}

/**
 * our reflection fromJson on compile time
 */
inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, object : com.google.gson.reflect.TypeToken<T>()
{}.type)  // covert from Json string to object

/**
 * our singular/plural word format
 * Ex:
 * - withNumber = true,  str = "time" -> 1 Time, 2 Times
 * - withNumber = false, str = "time" -> Time, Times
 */
fun singularPluralWordFormat(numberString: String, str: String, withNumber: Boolean = true): String {
    val number = numberString.toIntOrNull()

    if (withNumber) number?.let {
        return when (it) {
            0, 1 -> "$it $str"
            else -> "$it ${str}s"
        }
    } else number?.let {
        return when (it) {
            0, 1 -> str
            else -> "${str}s"
        }
    }

    return "Invalid"
}

/**
 * Round Double number into appropriate Double format
 * Original num = 234.4321323
 *  - KHR -> 200
 *  - USD -> 234.43
 */
@SuppressLint("DefaultLocale")
fun roundDoubleAmount(amount: Double, ccy: CCY): BigDecimal {
    val bigDecimalValue = BigDecimal(amount)
    return when(ccy) {
        CCY.RIEL -> {
            bigDecimalValue.divide(BigDecimal(100), RoundingMode.HALF_UP)
                .setScale(0, RoundingMode.HALF_UP)
                .multiply(BigDecimal(100))
                /*.toDouble()  // add later*/
        }
        CCY.DOLLAR -> {
            bigDecimalValue.setScale(2, RoundingMode.HALF_UP)
                /*.toDouble() // add later*/
        }
        CCY.DEFAULT -> {
            bigDecimalValue
        }
    }
}

/**
 * Formatting Double amount into String
 * Original amount = 143373943.94
 *  - KHR = 143,373,943.94 KHR
 *  - USD = 143,373,943.94 USD
 */
private val AmountDecimalFormat = java.text.DecimalFormat("#,###.##")
fun formatAmountWithCcy(amount: BigDecimal, ccy: String) = "${AmountDecimalFormat.format(amount)} $ccy"

/**
 * Convert format date form 'yyyy-MM-dd' -> 'MMMM dd, yyyy'
 * - Ex: 2026-01-03 -> June 03, 2026
 */
@RequiresApi(Build.VERSION_CODES.O)
fun convertDateFormat(inputDate: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")

    // Parse the input date string to a LocalDate object
    val date = LocalDate.parse(inputDate, inputFormatter)

    // Format the LocalDate object to the desired output format
    return date.format(outputFormatter)
}

/**
 * Get percentage value as decimal floating point
 */
fun Double.percentageToDouble() = this.div(other = 100)

/**
 * Get Year form months
 */
fun Int.getYearAsDouble() = this.div(other = 12.0)