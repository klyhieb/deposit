package kh.com.acleda.deposits.modules.openNewTerm.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.components.bottomSheet.BottomSheet
import kh.com.acleda.deposits.modules.home.domain.model.SelectAccountModel
import kh.com.acleda.deposits.modules.home.presentation.components.TextBalance
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.GlowingBlue2
import kh.com.acleda.deposits.ui.theme.GlowingBlue6
import kh.com.acleda.deposits.ui.theme.Gold8


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetSelectAccount(
    modifier: Modifier = Modifier,
    accountList: List<SelectAccountModel>,
    showBottomSheet: Boolean,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onClick: (SelectAccountModel) -> Unit
) {
    BottomSheet(
        title = "Select account to debit:",
        showBottomSheet = showBottomSheet,
        sheetState = sheetState,
        onDismiss = onDismiss
    ) {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(accountList) { account ->
                AccountItem(account = account) { selectedAccount ->
                    onClick(selectedAccount)
                }
            }
        }
    }
}

@Composable
fun AccountItem(
    modifier: Modifier = Modifier,
    account: SelectAccountModel,
    onClick: (SelectAccountModel) -> Unit
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        onClick = { onClick(account) },
        colors = CardDefaults.cardColors(
            containerColor = DepositsTheme.colors.uiFloated
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        modifier = modifier
            .height(64.dp)
            .fillMaxWidth()
            .padding(bottom = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AccountIndicator(color = account.accountIndicatorColor)

            Spacer(modifier = Modifier.width(4.dp))

            Column(
                Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = account.accountNumber,
                        style = MaterialTheme.typography.titleMedium,
                        color = DepositsTheme.colors.textHelp
                    )
                    TextBalance(
                        textStyle = MaterialTheme.typography.titleMedium,
                        balance = account.balance.toString(),
                        ccy = account.currency,
                        decimalPartColor = GlowingBlue6,
                        floatingPartColor = GlowingBlue2
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = account.type.dec,
                        style = MaterialTheme.typography.titleSmall,
                        color = DepositsTheme.colors.textSupport
                    )
                    Text(
                        text = account.currency.dec.uppercase(),
                        style = MaterialTheme.typography.titleSmall,
                        color = Gold8
                    )
                }
            }
        }
    }
}

@Composable
private fun AccountIndicator(color: Color, modifier: Modifier = Modifier) {
    Spacer(
        modifier
            .width(6.dp)
            .fillMaxHeight()
            .background(color = color)
    )
}

@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        AccountItem(account = SelectAccountModel()) {
        }
    }
}