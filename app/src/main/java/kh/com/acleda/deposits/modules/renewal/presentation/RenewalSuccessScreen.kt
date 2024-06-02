package kh.com.acleda.deposits.modules.renewal.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import kh.com.acleda.deposits.components.success.BaseSuccess
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Green3

@Composable
fun RenewalSuccessScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    BaseSuccess(
        modifier = modifier,
        title = "Renewal",
        successDescription = "Term Renewal was Completed",
        textButton = "view deposit list",
        onClick = onClick
    ) {
        Text(
            text = "The Rollover time, Maturity date, and all amounts has been updated, see detail in the list.",
            style = MaterialTheme.typography.labelLarge,
            color = Green3,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun SuccessPreview() {
    DepositsTheme {
        RenewalSuccessScreen()
    }
}