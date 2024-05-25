package kh.com.acleda.deposits.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.components.shape.TicketShape
import kh.com.acleda.deposits.core.DashLine
import kh.com.acleda.deposits.ui.theme.Blue0
import kh.com.acleda.deposits.ui.theme.Blue1
import kh.com.acleda.deposits.ui.theme.Blue4
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold2

@Composable
fun Ticket(
    modifier: Modifier = Modifier,
    containerColor: Color = Blue4.copy(0.7f),
    middlePercentage: Float,
    contentTop: @Composable ColumnScope.() -> Unit,
    contentBottom: @Composable ColumnScope.() -> Unit
) {
    val density = LocalDensity.current
    var composableWidth by remember { mutableStateOf(0.dp) }
    var composableHeight by remember { mutableStateOf(0.dp) }

    Surface(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                composableWidth = with(density) {
                    coordinates.size.width.toDp()
                }
                composableHeight = with(density) {
                    coordinates.size.height.toDp()
                }
            },
        color = containerColor,
        shape = TicketShape(
            circleVerticalPercentage = middlePercentage,
            circleRadius = 8.dp,
            cornerSize = CornerSize(8.dp)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val verticalLinePercentage = middlePercentage.minus(other = 0.5f)
            DashLine(
                itemWidth = 16f,
                spacing = 16f,
                strokeWidth = 3f,
                modifier = Modifier.offset(y = composableHeight.times(verticalLinePercentage))
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier
                        .weight(middlePercentage)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp)
                ) {
                    contentTop()
                }

                Surface(
                    color = Color.Transparent,
                    modifier = Modifier
                        .weight(1 - middlePercentage)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 4.dp)
                ) {
                    contentBottom()
                }
            }
        }
    }
}

@Preview
@Composable
private fun TicketShapeDemoPreview() {
    DepositsTheme {
        Ticket(
            modifier = Modifier
                .height(200.dp)
                .padding(horizontal = 24.dp),
            middlePercentage = 0.6f,
            contentTop = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.weight(0.5f)
                ) {
                    BadgeWithText(text = "Total to Receive:")

                    Text(
                        "103.06 USD",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Gold2
                    )
                }
            },
            contentBottom = {
                Column(
                    modifier = Modifier
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
                            "type",
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
                            "Term",
                            style = MaterialTheme.typography.titleSmall,
                            color = Blue0
                        )
                    }
                }
            }
        )
    }
}