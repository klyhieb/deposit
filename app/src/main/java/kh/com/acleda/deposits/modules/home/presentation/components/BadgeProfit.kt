package kh.com.acleda.deposits.modules.home.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold1

@Composable
fun BadgeProfit(
    modifier: Modifier = Modifier,
    amount: String,
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(1.dp, Gold1, RoundedCornerShape(12.dp))
            .padding(vertical = 3.dp, horizontal = 7.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Icon(painterResource(R.drawable.ic_crown), contentDescription = null, tint = Gold1)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = amount,
            color = DepositsTheme.colors.textSecondary,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.sp,
                letterSpacing = 0.5.sp
            )
        )
    }
}