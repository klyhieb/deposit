package kh.com.acleda.deposits.modules.home.domain.model

import androidx.compose.ui.graphics.Color
import kh.com.acleda.deposits.modules.home.presentation.components.CCY

data class SelectAccountModel(
    val accountNumber: String = "",
    val balance: Float = 0.0f,
    val currency: CCY = CCY.DEFAULT,
    val type: AccountType = AccountType.DEFAULT,
) {
    val accountIndicatorColor: Color = getAccountIndicatorColor(type)

    private fun getAccountIndicatorColor(accType: AccountType): Color {
        return when(accType){
            AccountType.WALLET -> Color(0XFF08BBD8)
            AccountType.BANK -> Color(0XFF9B59BA)
            AccountType.DEFAULT -> Color(0XFFEE554C)
        }
    }
}

enum class AccountType(val dec: String) {
    WALLET("VT"),
    BANK("T24"),
    DEFAULT("default")
}