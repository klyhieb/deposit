package kh.com.acleda.deposits.modules.stopRenewal.domain.model

data class StopRenewalConfirmModel(
    val depositAmount: String = "",
    val ccy: String = "",
    val mmNumber: String = "",
    val depositTypeId: String = "",
    val depositTerm: String = "",
    val autoRenewal: String = "",
    val rolloverTime: String = "",
    val maturityDate: String = "",
    val newRolloverTime: String = "",
    val newMaturityDate: String = ""
)
