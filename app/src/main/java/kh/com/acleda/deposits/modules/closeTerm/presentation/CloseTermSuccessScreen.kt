package kh.com.acleda.deposits.modules.closeTerm.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.components.success.BaseSuccess
import kh.com.acleda.deposits.core.formatAmountWithCcy
import kh.com.acleda.deposits.core.singularPluralWordFormat
import kh.com.acleda.deposits.modules.closeTerm.domain.model.AuthCloseTermModel
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold7
import java.math.BigDecimal

@Composable
fun CloseTermSuccessScreen (
    modifier: Modifier = Modifier,
    model: AuthCloseTermModel,
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
            text = "Deposit days:",
            style = MaterialTheme.typography.titleMedium,
            color = DepositsTheme.colors.textSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = singularPluralWordFormat(model.depositDays.toString(), "day"),
            style = MaterialTheme.typography.titleMedium,
            color = Gold7,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Total to Received:",
            style = MaterialTheme.typography.titleMedium,
            color = DepositsTheme.colors.textSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = formatAmountWithCcy(model.receivedInterest, model.ccy.dec.uppercase()),
            style = textBalanceStyle,
            color = Gold7,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        val testModel = AuthCloseTermModel(
            ccy = CCY.DOLLAR,
            depositDays = 10,
            receivedInterest = BigDecimal(12.00)
        )
        CloseTermSuccessScreen(model = testModel)
    }
}