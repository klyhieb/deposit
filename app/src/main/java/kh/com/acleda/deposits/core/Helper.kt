package kh.com.acleda.deposits.core

import com.google.gson.Gson
import kh.com.acleda.deposits.core.util.ExchangeRate
import kh.com.acleda.deposits.modules.home.domain.model.TermAmountModel
import kh.com.acleda.deposits.modules.home.presentation.components.CCY

fun safeConvertAccountBalance(termAmount: TermAmountModel): Float {
    val exchangeRate = ExchangeRate()

    // we try to safe convert Amount to Dollar in case it CCY.RIEL
    return if (termAmount.ccy == CCY.RIEL)
        exchangeRate.riel2Dollar(termAmount.amount)
    else
        termAmount.amount
}

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
