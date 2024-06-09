package kh.com.acleda.deposits.modules.depositList.presentation.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.components.TransparentCard
import kh.com.acleda.deposits.components.horizontalLineChart.HorizontalSumChat
import kh.com.acleda.deposits.modules.home.domain.model.SummaryDepositModel
import kh.com.acleda.deposits.modules.home.presentation.components.BadgeAdd
import kh.com.acleda.deposits.modules.home.presentation.components.BadgeCurrency
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import kh.com.acleda.deposits.modules.home.presentation.components.TextBalance
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Green2
import kh.com.acleda.deposits.ui.theme.Green5

@Composable
fun SummaryDepositList(
    modifier: Modifier = Modifier,
    summaryTermDeposit: SummaryDepositModel,
    isExpanded: Boolean,
    onClick: () -> Unit,
    onAddClick: () -> Unit,
    onExpendClick: () -> Unit
) {

    val totalKHR = summaryTermDeposit.summaryByCurrency
        .firstOrNull { it.name == CCY.RIEL.dec.uppercase() }
        ?.amountModel?.amount

    val totalInUSD = summaryTermDeposit.summaryByCurrency
        .firstOrNull { it.name == CCY.DOLLAR.dec.uppercase() }
        ?.amountModel?.amount

    val iconButton = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown

    val infiniteTransition  = rememberInfiniteTransition(label = "infiniteTransition")
    val offsetY  = infiniteTransition.animateValue(
        initialValue = 0.dp,
        targetValue = 10.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0.dp at 700 // ms
                8.dp at 200 using FastOutLinearInEasing
            },
            RepeatMode.Reverse
        ), label = "offsetY",
    )

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.Transparent,
        onClick = onClick,
        modifier = modifier
    ) {
        TransparentCard(
            widthBorder = 0.5.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioHighBouncy,
                                stiffness = Spring.StiffnessMedium
                            )
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        TextBalance(
                            balance = totalKHR.toString(),
                            ccy = CCY.RIEL,
                            textStyle = MaterialTheme.typography.titleLarge,
                            showCcySymbol = true
                        )

                        TextBalance(
                            balance = totalInUSD.toString(),
                            ccy = CCY.DOLLAR,
                            textStyle = MaterialTheme.typography.titleLarge,
                            showCcySymbol = true
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        BadgeAdd(
                            iconColor = Green5,
                            borderColor = Green2,
                            onClick = onAddClick
                        )

                        BadgeCurrency(text = "click to view detail")
                    }
                }

                Column {
                    if(isExpanded) {
                        Spacer(modifier = Modifier.height(20.dp))

                        // By Currency
                        SummaryDescription(
                            title = "By Currency: ",
                            maxWidth = 45.dp,
                            data = summaryTermDeposit.summaryByCurrency,
                            name = { it.name },
                            color = { it.amountModel.color }
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        HorizontalSumChat(
                            data = summaryTermDeposit.summaryByCurrency,
                            values = { it.amountModel.proportionAmount },
                            colors = { it.amountModel.color },
                            height = 14.dp,
                            roundCorner = 16.dp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // By Term Type
                        SummaryDescription(
                            title = "By Term Type: ",
                            maxWidth = 80.dp,
                            data = summaryTermDeposit.summaryInDollarByTypes,
                            name = { it.termType.mName },
                            color = { it.color }
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        HorizontalSumChat(
                            data = summaryTermDeposit.summaryInDollarByTypes,
                            values = { it.summaryAmountInDollar },
                            colors = { it.color },
                            height = 14.dp,
                            roundCorner = 16.dp
                        )
                    }
                }

                Icon(
                    iconButton,
                    tint = DepositsTheme.colors.iconInteractive,
                    contentDescription = null,
                    modifier = Modifier.offset(y = offsetY.value)
                        .align(alignment = Alignment.CenterHorizontally)
                        .clip(CircleShape)
                        .clickable { onExpendClick() }
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DepositsTheme {

    }
}