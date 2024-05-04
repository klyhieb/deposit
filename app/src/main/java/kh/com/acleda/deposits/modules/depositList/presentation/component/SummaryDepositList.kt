package kh.com.acleda.deposits.modules.depositList.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.components.TransparentCard
import kh.com.acleda.deposits.components.horizontalLineChart.HorizontalSumChat
import kh.com.acleda.deposits.modules.home.data.repository.DepositListRepo
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
    onClick: () -> Unit
) {

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
                        .height(54.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        TextBalance(
                            balance = "3332.50",
                            ccy = CCY.RIEL,
                            textStyle = MaterialTheme.typography.titleLarge
                        )

                        TextBalance(
                            balance = "4000",
                            ccy = CCY.DOLLAR,
                            textStyle = MaterialTheme.typography.titleLarge
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
                            onClick = {/*TODO*/ }
                        )

                        BadgeCurrency(text = "click to view detail")
                    }
                }

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
    }
}

@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        val summaryTermDeposit = DepositListRepo.getSummaryTermDeposit(LocalContext.current)

        SummaryDepositList(
            summaryTermDeposit = summaryTermDeposit,
            onClick = { }
        )
    }
}