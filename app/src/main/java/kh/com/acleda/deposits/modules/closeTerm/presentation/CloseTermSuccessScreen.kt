package kh.com.acleda.deposits.modules.closeTerm.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.components.success.BaseSuccess
import kh.com.acleda.deposits.modules.depositList.presentation.component.getCcyEnum
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import kh.com.acleda.deposits.modules.home.presentation.components.TextBalance
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold5
import kh.com.acleda.deposits.ui.theme.Gold7

@Composable
fun CloseTermSuccessScreen (
    modifier: Modifier = Modifier,
    totalReceived: Float = 0.0f,
    ccy: String? = CCY.DEFAULT.dec,
    onClick: () -> Unit = {}
) {
    val textBalanceStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
    BaseSuccess(
        modifier = modifier,
        title = "Close Term Deposit",
        successDescription = "Term Close was completed",
        textButton = "view deposit list",
        onClick = onClick
    ) {
        Text(
            text = "Total to Received:",
            style = MaterialTheme.typography.titleMedium,
            color = DepositsTheme.colors.textSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextBalance(
                balance = totalReceived.toString(),
                ccy = getCcyEnum(ccy),
                textStyle = textBalanceStyle,
                decimalPartColor = Gold7,
                floatingPartColor = Gold5
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = ccy.toString().uppercase(),
                style = textBalanceStyle,
                color = Gold7
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        CloseTermSuccessScreen()
    }
}