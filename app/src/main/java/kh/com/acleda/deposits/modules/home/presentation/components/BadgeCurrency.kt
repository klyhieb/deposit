package kh.com.acleda.deposits.modules.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.ui.theme.Badge
import kh.com.acleda.deposits.ui.theme.DepositsTheme

@Composable
fun BadgeCurrency(
    modifier: Modifier = Modifier,
    text: String
) {
    Surface(
        color = Badge,
        modifier = modifier.clip(RoundedCornerShape(4.dp))
    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelMedium,
            color = DepositsTheme.colors.textHelpLabel,
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp)
        )
    }
}

@Preview
@Composable
private fun CurrencyBadgePreview() {
    BadgeCurrency(text = "usd")
}