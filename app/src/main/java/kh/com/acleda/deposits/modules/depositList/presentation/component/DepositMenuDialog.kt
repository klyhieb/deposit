package kh.com.acleda.deposits.modules.depositList.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.components.BadgeWithText
import kh.com.acleda.deposits.components.dialog.BaseDialog
import kh.com.acleda.deposits.modules.home.data.repository.DepositListRepo
import kh.com.acleda.deposits.modules.home.domain.model.DepositItemModel
import kh.com.acleda.deposits.modules.home.presentation.components.TextBalance
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.White

@Composable
fun DepositMenuDialog(
    modifier: Modifier = Modifier,
    term: DepositItemModel,
    onDismissRequest: () -> Unit,
    onMenuClick: (DepositMenu) -> Unit
) {
    val termColor = getTermIndicatorColorById(term.termTypeId)
    val headerColor = arrayListOf(termColor, termColor.copy(alpha = 0.8f))

    val menuList = DepositMenu.entries

    BaseDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest
    ) {
        Column {
            Surface(
                color = Color.Transparent,
                modifier = Modifier
                    .background(Brush.horizontalGradient(headerColor))
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextBalance(
                            textStyle = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                            decimalPartColor = White,
                            balance = term.AmountOri.toString(),
                            ccy = getCcyEnum(term.currency)
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        Text(
                            text = term.currency ?: "",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                            color = White
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        BadgeWithText(
                            text = term.termTypeName ?: "",
                            textColor = termColor,
                            containerColor = White,
                            shape = RoundedCornerShape(16.dp),
                            textStyle = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = term.mm ?: "",
                        style = MaterialTheme.typography.titleSmall,
                        color = DepositsTheme.colors.textSecondary
                    )
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(menuList) {menu ->
                    TermMenuItem(
                        menu = menu,
                        onClick = { onMenuClick(menu) }
                    )
                }
            }
        }
    }
}

@Composable
fun TermMenuItem(
    modifier: Modifier = Modifier,
    menu: DepositMenu,
    onClick: () -> Unit
) {
    val isCloseTermMenu = menu == DepositMenu.CLOSE_TERM

    val color = if (isCloseTermMenu) {
        Color(0xFFC0223C)
    } else {
        Color(0xFF5D6472)
    }

    val height = if (isCloseTermMenu) 38.dp else 32.dp

    Column(
        modifier = Modifier.fillMaxWidth().height(height)
    ) {

        if (isCloseTermMenu) {
            HorizontalDivider(
                color = Color(0xFFEDEDED),
                modifier = modifier.padding(vertical = 4.dp)
            )
        }

        Surface(
            onClick = onClick,
            shape = RoundedCornerShape(3.dp),
            color = Color.Transparent
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 5.dp)
            ) {
                Icon(
                    painter = painterResource(menu.icon),
                    contentDescription = null,
                    tint = color
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = menu.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = color
                )
            }
        }
    }
}

enum class DepositMenu(val title: String, @DrawableRes val icon: Int) {
    RENEWAL("Renewal", R.drawable.ic_renewal),
    STOP_RENEWAL("Stop renewal", R.drawable.ic_stop_renewal),
    E_CERTIFICATE("E-Certificate", R.drawable.ic_e_certificate),
    CLOSE_TERM("Close term", R.drawable.ic_close_term)
}

@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        val terms = DepositListRepo.getDepositList(LocalContext.current)
        val term = terms.listMM[1]

        DepositMenuDialog(
            term = term,
            onDismissRequest = {},
            onMenuClick = {

            }
        )
    }
}

@Preview
@Composable
private fun PreviewItem() {
    DepositsTheme {
        TermMenuItem(
            menu = DepositMenu.STOP_RENEWAL,
            onClick = { }
        )
    }
}