package kh.com.acleda.deposits.core.util.decimalAmountTransformation

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

private val decimalSymbol = '.'

object InputFilterRegex {
    val DecimalInput by lazy { Regex("^(\\d*\\.?)+\$") }
}

fun filteredDecimalText(input: TextFieldValue): TextFieldValue {
    var inputText = input.text.replaceFirst(regex = Regex("^0+(?!$)"), "")
    var startsWithDot = input.text.startsWith(decimalSymbol)

    var selectionStart = input.selection.start
    var selectionEnd = input.selection.end

    if (startsWithDot) {
        inputText = "0$inputText"

        if (selectionStart == selectionEnd) {
            selectionStart++
            selectionEnd++
        } else {
            selectionEnd++
        }
    }

    val parts = inputText.split(decimalSymbol)
    var text = if (parts.size > 1) {
        parts[0] + decimalSymbol + parts.subList(1, parts.size).joinToString("")
    } else {
        parts.joinToString("")
    }

    if (text.startsWith(decimalSymbol)) {
        text = "0$text"
    }

    return input.copy(text = text, selection = TextRange(selectionStart, selectionEnd))
}