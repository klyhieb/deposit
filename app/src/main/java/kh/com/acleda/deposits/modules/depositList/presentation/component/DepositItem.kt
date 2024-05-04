package kh.com.acleda.deposits.modules.depositList.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.modules.home.data.repository.DepositListRepo
import kh.com.acleda.deposits.modules.home.domain.model.DepositItemModel
import kh.com.acleda.deposits.modules.home.domain.model.TermType
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import kh.com.acleda.deposits.modules.home.presentation.components.TextBalance
import kh.com.acleda.deposits.ui.theme.Black
import kh.com.acleda.deposits.ui.theme.Blue7
import kh.com.acleda.deposits.ui.theme.Blue9
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold7
import kh.com.acleda.deposits.ui.theme.Gray10
import kh.com.acleda.deposits.ui.theme.Gray9
import kh.com.acleda.deposits.ui.theme.Green0
import kh.com.acleda.deposits.ui.theme.Green7
import kh.com.acleda.deposits.ui.theme.Red0
import kh.com.acleda.deposits.ui.theme.White

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DepositList(
    modifier: Modifier = Modifier
) {
    val depositList = DepositListRepo.getDepositList(LocalContext.current)
    val listGroupByDate = depositList.listMM.groupBy { it.ValueDateOri }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        listGroupByDate.forEach { (date, items) ->
            stickyHeader {
                DepositDateHeader(date = date.toString())
            }
            items(items) { item ->
                DepositItem(
                    term = item,
                    onClick = {},
                    onOptionClick = {}
                )
            }
        }
    }
}

@Composable
fun DepositDateHeader(
    modifier: Modifier = Modifier,
    date: String,
    background: Color = Gray10.copy(alpha = 0.3f)
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        color = background
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 12.dp)
                .padding(start = 8.dp)
        ) {
            Icon(
                painterResource(id = R.drawable.ic_calendar),
                contentDescription = null,
                tint = DepositsTheme.colors.textPrimary
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = date,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = DepositsTheme.colors.textPrimary,
            )
        }
    }
}

@Composable
fun DepositItem(
    modifier: Modifier = Modifier,
    term: DepositItemModel,
    onClick: (DepositItemModel) -> Unit,
    onOptionClick: (DepositItemModel) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        onClick = { onClick(term) },
        colors = CardDefaults.cardColors(
            containerColor = getTermBackgroundColorByCcy(term.currency)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        modifier = modifier
            .height(110.dp)
            .fillMaxWidth()
            .padding(bottom = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TermIndicator(color = getTermIndicatorColorById(term.termTypeId))

            Column(
                Modifier.padding(vertical = 8.dp, horizontal = 7.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Icon(painterResource(
                            id = getTermIconById(term.termTypeId)),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        OutlineBadge(text = term.termName ?: "")
                    }

                    Icon(painterResource(id = R.drawable.ic_more),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .clickable { onOptionClick(term) }
                    )
                }

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = term.mm ?: "",
                    style = MaterialTheme.typography.titleSmall,
                    color = Black
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = term.interestRate ?: "",
                    style = MaterialTheme.typography.labelSmall,
                    color = DepositsTheme.colors.textSupport
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = term.maturityDate ?: "",
                        style = MaterialTheme.typography.labelSmall,
                        color = DepositsTheme.colors.textSupport
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextBalance(
                            textStyle = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            balance = term.AmountOri.toString(),
                            ccy = getCcyEnum(term.currency),
                            decimalPartColor = Blue9,
                            floatingPartColor = Blue7
                        )
                        
                        Spacer(modifier = Modifier.width(2.dp))

                        Text(
                            text = term.currency ?: "",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Blue9
                        )
                    }

                }
            }
        }
    }
}

fun getTermIndicatorColorById(id: String?): Color {
    return when (id) {
        TermType.HI_INCOME.id -> Gold7
        TermType.HI_GROWTH.id -> Blue7
        TermType.LONG_TERM.id -> Green7
        else -> White
    }
}

fun getTermBackgroundColorByCcy(id: String?): Color {
    return when (id) {
        CCY.RIEL.dec.uppercase() -> Green0
        CCY.DOLLAR.dec.uppercase() -> Red0
        else -> White
    }
}

fun getTermIconById(id: String?): Int {
    return when (id) {
        TermType.HI_INCOME.id -> R.drawable.ic_hi_income
        TermType.HI_GROWTH.id -> R.drawable.ic_hi_growth
        TermType.LONG_TERM.id -> R.drawable.ic_long_term
        else -> R.drawable.ic_drop_down
    }
}

fun getCcyEnum(ccy: String?): CCY {
    return when(ccy)
    {
        CCY.RIEL.dec.uppercase() -> CCY.RIEL
        CCY.DOLLAR.dec.uppercase() -> CCY.DOLLAR
        else -> CCY.DEFAULT
    }
}

@Composable
private fun TermIndicator(color: Color, modifier: Modifier = Modifier) {
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
        Surface(

            color = DepositsTheme.colors.brand
        ) {
            DepositList()
        }
    }
}
