package kh.com.acleda.deposits.modules.home.domain.model

import androidx.compose.ui.graphics.Color
import kh.com.acleda.deposits.core.safeConvertAccountBalance
import kh.com.acleda.deposits.modules.home.presentation.components.CCY


data class SummaryDepositModel(
    val summaryByCurrency: List<SummaryCurrencyModel> = listOf(),
    val summaryInDollarByTypes: List<DepositTypeModel> = listOf(),
    val summaryByTypes: List<DepositTypeModel> = listOf()
)

data class SummaryCurrencyModel(
    val name: String,
    val amountModel: TermAmountModel
)

data class DepositTypeModel(
    val termType: TermType,
    val color: Color,
    val totalAmountByCCY: List<TermAmountModel> = listOf(),
    val summaryAmountInDollar: Float = 0.0f
) {
    val totalAmountInDollar: Float = totalAmountByCCY
        .map { termAmount ->
            safeConvertAccountBalance(
                model = termAmount,
                ccy = { termAmount.ccy },
                amount = { termAmount.amount }
            )
        }.sum()
}

data class TermAmountModel(
    val color: Color,
    val ccy: CCY,
    val amount: Float = 0.0f
) {
    // this storing amount for pie proportion
    // mean we convert riel to dollar for proportion
    val proportionAmount: Float = safeConvertAccountBalance(
        model = this,
        ccy = { this.ccy },
        amount = { this.amount }
    )
}

enum class TermType(val id: String, val mName: String) {
    HI_INCOME("21010", "Hi-Income"), HI_GROWTH("21011", "Hi-Growth"), LONG_TERM("21034", "Long Term")
}
