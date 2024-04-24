package kh.com.acleda.deposits.core.util


val exchangeRate: HashMap<String, Float> = hashMapOf(
    "Riel" to 200f,
    "Dollar" to 4060f
)

class ExchangeRate {
    fun riel2Dollar(amount: Float): Float {
        return amount / exchangeRate["Dollar"]!!
    }
}