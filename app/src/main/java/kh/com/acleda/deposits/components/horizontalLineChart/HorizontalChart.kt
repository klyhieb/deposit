package kh.com.acleda.deposits.components.horizontalLineChart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.modules.home.data.repository.DepositListRepo
import kh.com.acleda.deposits.ui.theme.DepositsTheme

@Composable
fun <T> HorizontalSumChat(
    data: List<T>,
    values: (T) -> Float,
    colors: (T) -> Color,
    height: Dp,
    roundCorner: Dp
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(roundCorner))) {
        data.forEach { item: T ->
            Spacer(
                modifier = Modifier
                    .weight(values(item) + 0.1f)
                    .height(height)
                    .background(colors(item))
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        /*val context = LocalContext.current
        val summaryTermDeposit = DepositListRepo.getSummaryTermDeposit(context)
        val totalByCurrency = DepositListRepo.getSummaryTermDepositListScreen(LocalContext.current)

        HorizontalSumChat(
            data = summaryTermDeposit.summaryInDollarByTypes,
            values = { it. },
            colors = { it.color },
            height = 10.dp,
            roundCorner = 8.dp
        )*/
    }
}