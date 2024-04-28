package kh.com.acleda.deposits.modules.openNewTerm.domain.model

data class OpenTermDepositModel(
    val type: String? = "",
    val term: Int = 0,
    val amount: Float? = 0.0f,
    val interestRate: Float? = 0.0f,
    val interestRateAmount: Float? = 0.0f,
    val tax: Float? = 0.0f,
    val effectiveDate: String? = "",
    val maturityDate: String? = "",
    val autoRenewal: String? = "",
    val totalToReceive: Float? = 0.0f
)