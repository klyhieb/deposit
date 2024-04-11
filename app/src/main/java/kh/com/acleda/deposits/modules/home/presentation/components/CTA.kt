package kh.com.acleda.deposits.modules.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.components.BTN_TYPE
import kh.com.acleda.deposits.components.DepositButton
import kh.com.acleda.deposits.components.TransparentCard
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold4

@Composable
fun CTA(
    modifier: Modifier = Modifier,
    onWithdraw: () -> Unit,
    onDeposit: () -> Unit,
    onChangeMainWallet: () -> Unit
) {
    TransparentCard(widthBorder = 0.5.dp, modifier = Modifier.fillMaxWidth()) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                DepositButton(
                    text = "Withdraw",
                    type = BTN_TYPE.FILL,
                    colors = listOf(Gold4, Gold4),
                    iconDrawable = R.drawable.ic_recieve,
                    onClick = {},
                )

                Spacer(modifier = Modifier.width(8.dp))

                DepositButton(
                    text = "Deposit",
                    type = BTN_TYPE.OUTLINE,
                    colors = listOf(Gold4),
                    iconDrawable = R.drawable.ic_send,
                    onClick = {},
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))

            DepositButton(
                text = "Change Main Wallet",
                type = BTN_TYPE.FILL,
                colors = DepositsTheme.colors.gradientButton,
                iconDrawable = R.drawable.ic_wallet,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
        }
    }
}