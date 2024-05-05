package kh.com.acleda.deposits.modules.home.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Green2
import kh.com.acleda.deposits.ui.theme.Green5
import kh.com.acleda.deposits.ui.theme.White

@Composable
fun  BadgeAdd(
    modifier: Modifier = Modifier,
    iconColor: Color,
    borderColor: Color,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = White.copy(alpha = 0.2f),
        border = BorderStroke(0.8.dp, borderColor),
        shape = CircleShape,
        modifier = modifier.size(28.dp).padding(1.dp)
    ) {
        Icon(
            painterResource(R.drawable.ic_add),
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        BadgeAdd(
            iconColor = Green5,
            borderColor = Green2,
            onClick = {/*TODO*/}
        )
    }
}