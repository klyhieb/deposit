package kh.com.acleda.deposits.modules.home.presentation.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BodyContainer(
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    topPadding: Dp = 80.dp,
    paddingBottom: Dp = 24.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(64.dp)
        )

        Column(modifier = Modifier.verticalScroll(scrollState)) {
            Spacer(modifier = Modifier.height(topPadding))

            Surface(
                color = Color.Transparent,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = modifier.padding(horizontal = 16.dp)
                        .navigationBarsPadding()
                        .padding(bottom = paddingBottom),
                    content = content
                )
            }
        }
    }
}