package kh.com.acleda.deposits.core

import com.google.gson.Gson
import kh.com.acleda.deposits.core.util.ExchangeRate
import kh.com.acleda.deposits.modules.home.domain.model.TermAmountModel
import kh.com.acleda.deposits.modules.home.presentation.components.CCY

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
 * our singular/plural word format Ex: 1 Time, 2 Times
 */
fun singularPluralWordFormat(numberString: String, str: String): String {
    val number = numberString.toIntOrNull()

    number?.let {
        return when (it) {
            0, 1 -> "$it $str"
            else -> "$it ${str}s"
        }
    }
    return "Invalid"
}