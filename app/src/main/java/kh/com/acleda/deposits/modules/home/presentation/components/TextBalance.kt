package kh.com.acleda.deposits.modules.home.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import kh.com.acleda.deposits.ui.theme.Gray3
import kh.com.acleda.deposits.ui.theme.White
import java.text.DecimalFormat

@Composable
fun TextBalance(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    decimalPartColor: Color = White,
    floatingPartColor: Color = Gray3,
    balance: String,
    ccy: CCY
) {
    val values: List<String> = balance.split('.')
    val currency = when (ccy) {
        CCY.DOLLAR -> "$"
        CCY.RIEL -> "៛ "
        CCY.DEFAULT -> ""
    }

    val dec = DecimalFormat("#,###.##")
    val decimal = dec.format(values[0].toInt())
    val float = if (values.size > 1)
        when {
            values[1].toInt() == 0 -> "00"
            values[1].count() == 1 -> values[1] + "0"
            values[1].isEmpty() -> "00"
            else -> values[1]
        } else "00"

    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            append("$currency ") // currency
            append(decimal)   // decimal
            append(".") // dot sign
            withStyle(style = SpanStyle(color = floatingPartColor)) {// floating point
                append(float)
            }
        },
        color = decimalPartColor,
        style = textStyle
    )
}

enum class CCY(val dec: String) {
    DOLLAR("usd"),
    RIEL("khr"),
    DEFAULT("default")
}