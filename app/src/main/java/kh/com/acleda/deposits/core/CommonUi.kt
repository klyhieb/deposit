package kh.com.acleda.deposits.core

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import kh.com.acleda.deposits.modules.home.presentation.components.TextBalance
import java.text.DecimalFormat

/**
 * Used with accounts and bills to create the animated circle.
 */
fun <E> List<E>.extractProportions(selector: (E) -> Float): List<Float> {
    val total = this.sumOf { selector(it).toDouble() }
    return this.map { (selector(it) / total).toFloat() }
}

fun formatAmount(amount: Float?): String {
    if (amount == null) return ""
    return AmountDecimalFormat.format(amount)
}

private val AccountDecimalFormat = DecimalFormat("####")
private val AmountDecimalFormat = DecimalFormat("#,###.##")

/**
 * A vertical colored line that is used in a to differentiate accounts.
 */
@Composable
private fun TermIndicator(color: Color, modifier: Modifier = Modifier) {
    Spacer(
        modifier
            .size(4.dp, 12.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = color)
    )
}

@Composable
fun TermRow(
    modifier: Modifier = Modifier,
    color: Color,
    amount: String,
    ccy: CCY
) {
    Row (verticalAlignment = Alignment.CenterVertically) {
        TermIndicator(color = color)
        Spacer(modifier = Modifier.width(4.dp))
        TextBalance(balance = amount, ccy = ccy, textStyle = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun DashLine(
    modifier: Modifier = Modifier,
    lineColor: Color
) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Canvas(modifier.fillMaxWidth().height(1.dp)) {

        drawLine(
            color = lineColor,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )
    }
}


@Preview
@Composable
private fun Preview() {
    TermRow(
        color = Color.Red,
        amount = "200.10",
        ccy = CCY.DOLLAR
    )
}