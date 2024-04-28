package kh.com.acleda.deposits.modules.openNewTerm.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.White

@Composable
fun TermDescription(
    modifier: Modifier = Modifier,
    title: String,
    @DrawableRes icon: Int
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Surface(
            shape = RoundedCornerShape(100),
            contentColor = White.copy(alpha = 0.2f),
            modifier = Modifier.size(72.dp)
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = DepositsTheme.colors.brandSecondary,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
            color = DepositsTheme.colors.textPrimary
        )
    }
}