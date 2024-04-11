package kh.com.acleda.deposits.modules.home.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.ui.theme.Gold2
import kh.com.acleda.deposits.ui.theme.White

@Composable
fun BadgeAdd(
    modifier: Modifier = Modifier,
) {
    Surface(
        color = White.copy(alpha = 0.3f),
        modifier = modifier
            .clip(CircleShape)
    ) {
        Icon(
            painterResource(R.drawable.ic_add),
            contentDescription = null,
            tint = Gold2,
            modifier = Modifier.padding(4.dp)
        )
    }
}