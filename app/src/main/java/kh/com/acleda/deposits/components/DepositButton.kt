package kh.com.acleda.deposits.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.ui.theme.Gold4
import kh.com.acleda.deposits.ui.theme.White

@Composable
fun DepositButton(
    modifier: Modifier = Modifier,
    iconDrawable: Int? = null,
    text: String,
    type: BTN_TYPE,
    colors: List<Color>,
    onClick: () -> Unit
) {
    val border: BorderStroke?
    val gradient: Brush
    val textColor: Color

    when (type) {
        BTN_TYPE.OUTLINE -> {
            border = BorderStroke(2.dp, Gold4)
            gradient = Brush.horizontalGradient(listOf(Color.Transparent, Color.Transparent))
            textColor = Gold4
        }

        BTN_TYPE.FILL -> {
            border = null
            gradient = Brush.horizontalGradient(colors)
            textColor = White
        }

    }

    Button(
        onClick = onClick,
        border = border,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(32.dp),
        contentPadding = PaddingValues(),
        modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(32.dp))
                .background(gradient)
                .padding(vertical = 20.dp, horizontal = 22.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (iconDrawable != null) {
                    Icon(painterResource(iconDrawable), contentDescription = "null", tint = White)

                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    text,
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor,
                    maxLines = 1
                )
            }
        }
    }
}


enum class BTN_TYPE {
    OUTLINE, FILL
}