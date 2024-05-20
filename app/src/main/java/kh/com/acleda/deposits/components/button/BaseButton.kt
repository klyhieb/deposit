package kh.com.acleda.deposits.components.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.ui.theme.Gold6
import kh.com.acleda.deposits.ui.theme.Gray1

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    shape: Shape = ButtonDefaults.shape,
    enable: Boolean = true,
    text: String,
    textColor: Color,
    bodyColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = bodyColor,
            contentColor = textColor,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,

        ),
        shape = shape,
        enabled = enable,
        modifier = modifier.height(54.dp).fillMaxWidth()
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold
            ),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    BaseButton(
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
        text = "Deposit",
        textColor = Gray1,
        bodyColor = Gold6
    ) {

    }
}