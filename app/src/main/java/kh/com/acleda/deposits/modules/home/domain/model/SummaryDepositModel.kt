package kh.com.acleda.deposits.modules.home.domain.model

import androidx.compose.ui.graphics.Color
import kh.com.acleda.deposits.core.safeConvertAccountBalance
import kh.com.acleda.deposits.modules.home.presentation.components.CCY


data class SummaryDepositModel(
    val summaryInDollarByTypes: List<DepositTypeModel> = listOf(),
    val summaryByTypes: List<DepositTypeModel> = listOf()
)

data class DepositTypeModel(
    val termType: TermType,
    val color: Color,
    val totalAmountByCCY: List<TermAmountModel> = listOf(),
    val summaryAmountInDollar: Float = 0.0f
)

data class TermAmountModel(
    val color: Color,
    val ccy: CCY,
    val amount: Float = 0.0f
) {
    // this storing amount for pie proportion
    // mean we convert riel to dollar for proportion
    val proportionAmount: Float = safeConvertAccountBalance(this)
}

enum class TermType(val id: String) {
    HI_INCOME("21010"), HI_GROWTH("21011"), LONG_TERM("21034")
}
