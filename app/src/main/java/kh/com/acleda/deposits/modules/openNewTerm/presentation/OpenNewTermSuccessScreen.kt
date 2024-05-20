package kh.com.acleda.deposits.modules.openNewTerm.presentation

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
fun OpenNewTermSuccessScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    BaseSuccess(
        modifier = modifier,
        title = "Open New Term",
        successDescription = "Completed",
        textButton = "view deposit list",
        onClick = onClick
    ) {
        Text(
            text = "Please check your new term in the list.",
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
        OpenNewTermSuccessScreen()
    }
}