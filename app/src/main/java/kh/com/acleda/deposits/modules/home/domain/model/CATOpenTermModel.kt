package kh.com.acleda.deposits.modules.home.domain.model

import androidx.compose.ui.graphics.Color
import kh.com.acleda.deposits.ui.theme.Blue10

data class CATOpenTermModel(
    val hiIncome: CATTermModel,
    val hiGrowth: CATTermModel,
    val longTerm: CATTermModel
)

data class CATTermModel(
    val id: String,
    val name: String,
    val icon: Int,
    val minMonth: Int,
    val minMonthRate: Float,
    val mainColor: Color,
    val supportColor: Color
) {
    val iconColor: Color = Blue10
}