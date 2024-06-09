package kh.com.acleda.deposits.components.piechart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.core.extractProportions
import kh.com.acleda.deposits.core.formatAmount
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import kh.com.acleda.deposits.modules.home.presentation.components.TextBalance

@Composable
fun <T> PieChart(
    modifier: Modifier = Modifier,
    items: List<T>,
    colors: (T) -> Color,
    preferStroke: Dp,
    amounts: (T) -> Float,
    amountsTotalInRiel: Float,
    amountsTotalInDollar: Float,
    circleLabel: String,
    isHasCenterLabel: Boolean
    /*rows: @Composable (T) -> Unit*/
    ) {
    Column(modifier) {
        Box {
            val accountsProportion = items.extractProportions { amounts(it) }
            val circleColors = items.map { colors(it) }
            AnimatedCircle(
                proportions = accountsProportion,
                colors = circleColors,
                preferStroke = preferStroke,
                modifier = Modifier
                    .height(300.dp)
                    .align(Alignment.Center)
                    .fillMaxWidth()
            )

            if (isHasCenterLabel) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    if (circleLabel.isNotEmpty()) {
                        Text(
                            text = circleLabel,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                    TextBalance(
                        textStyle = MaterialTheme.typography.labelLarge,
                        balance = amountsTotalInRiel.toString(),
                        ccy = CCY.RIEL,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        showCcySymbol = true
                    )

                    TextBalance(
                        textStyle = MaterialTheme.typography.labelLarge,
                        balance = amountsTotalInDollar.toString(),
                        ccy = CCY.DOLLAR,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        showCcySymbol = true
                    )
                }
            }
        }
        /*Spacer(Modifier.height(10.dp))
        Card {
            Column(modifier = Modifier.padding(12.dp)) {
                items.forEach { item ->
                    rows(item)
                }
            }
        }*/
    }
}