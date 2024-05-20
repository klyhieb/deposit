package kh.com.acleda.deposits.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gray10
import kh.com.acleda.deposits.ui.theme.Red4
import kh.com.acleda.deposits.ui.theme.White

@Composable
fun BaseDialog(
    modifier: Modifier = Modifier,
    containerColor: Color = White,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(
                containerColor = containerColor
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
            ) {
                Column {
                    content()
                }
            }
        }
    }
}

@Composable
fun CloseTermDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    val warningContainerColor = Color(0xFFFFF7EA)

    BaseDialog(
        containerColor = warningContainerColor,
        modifier = modifier,
        onDismissRequest = onDismissRequest
    ) {
        Row {
            Surface(
                color = Color(0xFFF18F00),
                shape = CircleShape,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.warning),
                    contentDescription = null,
                    tint = DepositsTheme.colors.iconInteractive,
                    modifier = Modifier.padding(5.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "You are about to close this term deposit?",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF624E4E)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider()

        Box(
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    modifier = Modifier.weight(0.5f),
                    shape = RectangleShape,
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.titleMedium,
                        color = Gray10,
                        textAlign = TextAlign.Center
                    )
                }

                TextButton(
                    modifier = Modifier.weight(0.5f),
                    shape = RectangleShape,
                    onClick = onConfirm
                ) {
                    Text(
                        text = "Confirm",
                        style = MaterialTheme.typography.titleMedium,
                        color = Red4,
                        textAlign = TextAlign.Center
                    )
                }
            }

            VerticalDivider(Modifier.height(32.dp))
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        CloseTermDialog(
            onDismissRequest = {},
            onConfirm = {}
        )
    }
}