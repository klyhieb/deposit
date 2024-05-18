package kh.com.acleda.deposits.modules.openNewTerm.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.components.BadgeWithText
import kh.com.acleda.deposits.components.CenterTopAppBar
import kh.com.acleda.deposits.components.button.SUButton
import kh.com.acleda.deposits.core.DashLine
import kh.com.acleda.deposits.modules.openNewTerm.domain.model.OpenTermDepositModel
import kh.com.acleda.deposits.components.shape.TicketShape
import kh.com.acleda.deposits.ui.theme.Blue0
import kh.com.acleda.deposits.ui.theme.Blue1
import kh.com.acleda.deposits.ui.theme.Blue4
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold2
import kh.com.acleda.deposits.ui.theme.Gold6
import kh.com.acleda.deposits.ui.theme.Gray1
import kh.com.acleda.deposits.ui.theme.White

@Composable
fun OpenNewTermConfirmScreen(
    modifier: Modifier = Modifier,
    summary: OpenTermDepositModel,
    onBackPress: () -> Unit = {},
    onClickConfirm: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    CenterTopAppBar(
        title = "Open New Term",
        onBackClick = { /*TODO*/ }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier
                        .height(274.dp)
                        .padding(horizontal = 32.dp),
                    color = Blue4.copy(0.7f),
                    shape = TicketShape(circleRadius = 8.dp, cornerSize = CornerSize(4.dp))
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        DashLine(lineColor = White, stroke = 6.dp)

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.weight(0.5f)
                            ) {
                                BadgeWithText(text = "Total to Receive:",)

                                Text(
                                    "103.06 USD",
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = Gold2
                                )
                            }

                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.weight(0.5f).padding(horizontal = 16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Text(
                                        "Deposit Type:",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = Blue1
                                    )

                                    Text(
                                        summary.type!!,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = Blue0
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Text(
                                        "Deposit Term:",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = Blue1
                                    )

                                    Text(
                                        summary.term.toString(),
                                        style = MaterialTheme.typography.titleSmall,
                                        color = Blue0
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Text(
                                        "Deposit Amount:",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = Blue1
                                    )

                                    Text(
                                        summary.amount.toString(),
                                        style = MaterialTheme.typography.titleSmall,
                                        color = Blue0
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Text(
                                        "Auto-Renewal:",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = Blue1
                                    )

                                    Text(
                                        summary.autoRenewal!!,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = Blue0
                                    )
                                }
                            }

                        }
                    }
                }
            }

            SUButton(
                shape = RectangleShape,
                text = "Confirm",
                textColor = Gray1,
                bodyColor = Gold6,
                onClick = onClickConfirm
            )
        }
    }
}


@Preview
@Composable
fun TicketShapePreview() {
    DepositsTheme {
        OpenNewTermConfirmScreen(
            summary = OpenTermDepositModel()
        )
    }

}

