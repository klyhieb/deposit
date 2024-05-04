package kh.com.acleda.deposits.modules.depositList.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kh.com.acleda.deposits.ui.theme.Black
import kh.com.acleda.deposits.ui.theme.DepositsTheme

@Composable
fun OutlineBadge(
    modifier: Modifier = Modifier,
    text: String
) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = Color.Transparent,
        border = BorderStroke(0.7.dp, Black),
        modifier = modifier
    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
            color = DepositsTheme.colors.textHelpLabel,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0)
@Composable
private fun Preview() {
    DepositsTheme {
        OutlineBadge(text = "Hi-Growth")
    }
}