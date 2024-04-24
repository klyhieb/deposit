package kh.com.acleda.deposits.modules.home.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.components.TransparentCard
import kh.com.acleda.deposits.ui.theme.DepositsTheme

@Composable
fun Section(
    modifier: Modifier = Modifier,
    label: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Box {
        TransparentCard(widthBorder = 0.5.dp, modifier = Modifier
            .fillMaxWidth()
        ) {
            Column(modifier = modifier.padding(top = 24.dp),
                content = content
            )
        }

        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = DepositsTheme.colors.textPrimary,
            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
        )
    }
   
}