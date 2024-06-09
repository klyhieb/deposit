package kh.com.acleda.deposits.modules.closeTerm.domain.model

import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import java.math.BigDecimal

data class AuthCloseTermModel(
    val ccy: CCY = CCY.DEFAULT,
    val depositDays: Long = 0,
    val receivedInterest: BigDecimal = BigDecimal(0)
)
