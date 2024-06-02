package kh.com.acleda.deposits.modules.renewal.domain.model

import java.math.BigDecimal

data class UnAuthRenewalModel(
    var depositAmount: Double = 0.0,
    var ccy: String = "",
    var mm: String = "",
    var depositType:  String = "",
    var depositTerm: Int = 0,
    var rolloverTime: Int = 0,
    var maturityDate: String = "",
    var autoRenewal: String = "",

    // fields has effect update
    var newRolloverTime: Int = 0,
    var newMaturityDate: String = "",
    var newTotalInterest: BigDecimal = BigDecimal(0),
    var newTax: BigDecimal = BigDecimal(0),
    var newNetMonthlyInterest: BigDecimal = BigDecimal(0),
    var newTotalToReceiveAtFinalMaturity: BigDecimal = BigDecimal(0)
)
