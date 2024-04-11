package kh.com.acleda.deposits.modules.home.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    balance: String,
    ccy: CCY
) {
    val values: List<String> = balance.split('.')
    val currency = when (ccy) {
        CCY.DOLLAR -> "$"
        CCY.RIEL -> "៛"
    }

    val dec = DecimalFormat("#,###.##")
    val decimal = dec.format(values[0].toInt())

    Text(
        buildAnnotatedString {
            append("$currency ") // currency
            append(decimal)   // decimal
            append(".") // dot sign
            withStyle(style = SpanStyle(color = Gray3)) {// floating point
                append(values[1])
            }
        },
        color = White,
        style = textStyle
    )
}

enum class CCY(val dec: String) {
    DOLLAR("usd"),
    RIEL("khr")
}