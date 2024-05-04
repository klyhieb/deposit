package kh.com.acleda.deposits.modules.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.components.TransparentCard
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold2

@Composable
fun MainBalance(
    modifier: Modifier = Modifier,
    onViewBalance: () -> Unit,
    onAddNewWallet: () -> Unit,
) {
    TransparentCard(widthBorder = 0.5.dp, modifier = modifier.fillMaxWidth()) {
        val currency = CCY.DOLLAR

        Column(
            modifier = Modifier.clickable { onViewBalance() }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Your Balance",
                    color = DepositsTheme.colors.textPrimary,
                    style = MaterialTheme.typography.labelLarge
                )

                BadgeProfit(amount = "$114")
            }
            
            Spacer(modifier = Modifier.height(10.dp))

            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextBalance(balance = "3345.87", ccy = currency)

                Spacer(modifier = Modifier.width(8.dp))

                BadgeCurrency(text = currency.dec)

                Spacer(modifier = Modifier.weight(1f))

                BadgeAdd(
                    iconColor = Gold2,
                    borderColor = Color.Transparent,
                    onClick = onAddNewWallet
                )
            }
        }
    }
}