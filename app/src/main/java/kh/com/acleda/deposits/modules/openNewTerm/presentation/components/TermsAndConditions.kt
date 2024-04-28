package kh.com.acleda.deposits.modules.openNewTerm.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold8
import kh.com.acleda.deposits.ui.theme.Gray6
import kh.com.acleda.deposits.ui.theme.White

@Composable
fun TermsAndConditions(
    modifier: Modifier = Modifier,
    onClick: (Boolean) -> Unit
) {
    var selected by remember { mutableStateOf(false) }

    Surface(
        color = White
    ) {
        Column(modifier = modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selected,
                    onClick = {
                        selected = !selected
                        onClick(selected)
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Gold8,
                        unselectedColor = Gray6
                    )
                )

                Text(
                    buildAnnotatedString {
                        append("I have read and agree to the")
                        withStyle(style = SpanStyle(color = Gold8)) {
                            append("Terms & Conditions ")
                        }
                    },
                    color = DepositsTheme.colors.textSecondary,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = modifier.clickable {
                        selected = !selected
                        onClick(selected)
                    }
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        TermsAndConditions {

        }
    }
}