package kh.com.acleda.deposits.modules.renewal.domain.model

data class UnAuthRenewalModel(
    var depositAmount: Double = 0.0,
    var contactNumber: String = "",
    var depositType:  String = "",
    var depositTerm: Int = 0,
    var rolloverTime: Int = 0,
    var autoRenewal: String = "",

    // fields has effect update
    var newRolloverTime: Int = 0,
    var newMaturityDate: String = "",
    var newTotalInterest: Double = 0.0,
    var newTax: Double = 0.0,
    var newNetMonthlyInterest: Double = 0.0,
    var newTotalToReceiveAtFinalMaturity: Double = 0.0
)
