package kh.com.acleda.deposits.modules.openNewTerm.domain.model

import java.math.BigDecimal

data class UnAthOpenTermModel(
    var showAccountNumber: String = "",
    var accountName: String = "",
    var type: String = "",
    var term: Int = 0,
    var principalAmount: BigDecimal = BigDecimal(0),
    var ccy: String = "",
    var taxRate: Float = 0.0f,
    var creditMonths: Int = 0,
    var interestInCreditMonths: BigDecimal = BigDecimal(0),
    var taxAmountInCreditMonth: BigDecimal = BigDecimal(0),
    var effectiveDate: String = "",
    var maturityDate: String = "",
    var finalMaturityDate: String = "",
    var rolloverTime: Int = 0,
    var autoRenewal: String = "",
    var netInterestInCreditMonths: BigDecimal = BigDecimal(0),
    var totalReceivedAtCreditMonths: BigDecimal = BigDecimal(0),
    var totalToReceiveAtFinalMaturity: BigDecimal = BigDecimal(0),
    var totalNetInterest: BigDecimal = BigDecimal(0)
)