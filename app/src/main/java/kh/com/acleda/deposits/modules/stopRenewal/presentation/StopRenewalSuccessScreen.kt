package kh.com.acleda.deposits.modules.stopRenewal.presentation

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
fun StopRenewalSuccessScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    BaseSuccess(
        modifier = modifier,
        title = "Stop Renewal",
        successDescription = "Term Stop Renewal was Completed",
        textButton = "view deposit list",
        onClick = onClick
    ) {
        Text(
            text = "Your term will close in new maturity date.",
            style = MaterialTheme.typography.labelLarge,
            color = Green3,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        StopRenewalSuccessScreen()
    }
}