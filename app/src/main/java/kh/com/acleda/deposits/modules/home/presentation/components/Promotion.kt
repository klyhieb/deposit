package kh.com.acleda.deposits.modules.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Promotion1
import kh.com.acleda.deposits.ui.theme.Promotion2
import kh.com.acleda.deposits.ui.theme.Promotion3

@Composable
fun PromoCashWithdrawal(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.Transparent,
        onClick = onClick,
    ) {
        val gradient = Brush.horizontalGradient(listOf(Promotion1, Promotion2, Promotion3))

        Box(modifier = Modifier.background(gradient)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .height(84.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column {
                    Text(
                        "Cash withdrawal",
                        style = MaterialTheme.typography.labelLarge,
                        color = DepositsTheme.colors.textPrimary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    BadgeCurrency(text = "0% in every bank")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Image(
                    painter = painterResource(id = R.drawable.money),
                    contentDescription = null,
                    modifier = Modifier
                        .height(65.dp)
                        .width(164.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PromoCashWithdrawalPreview() {
    DepositsTheme {
        PromoCashWithdrawal(onClick = {})
    }
}