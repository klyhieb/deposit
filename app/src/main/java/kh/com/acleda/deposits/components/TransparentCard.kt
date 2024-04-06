package kh.com.acleda.deposits.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.ui.theme.TransparentCardBorder

@Composable
fun TransparentCard(
    modifier: Modifier = Modifier,
    opacity: Float = 0.3f,
    widthBorder: Dp = 0.dp,
    colorBorder: Color = TransparentCardBorder,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = Color.White.copy(opacity),
        border = BorderStroke(width = widthBorder, color = colorBorder)
    ) {
        Column(modifier = modifier.padding(8.dp),
            content = content
        )
    }
}