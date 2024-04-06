package kh.com.acleda.deposits.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.components.TransparentCard
import kh.com.acleda.deposits.components.WaveLines
import kh.com.acleda.deposits.modules.home.presentation.components.BodyContainer
import kh.com.acleda.deposits.modules.home.presentation.components.TopAppBar
import kh.com.acleda.deposits.ui.theme.DepositsTheme

@Composable
fun DepositsApp() {
    DepositsTheme {
        Box {
            val mainBackgroundColor = DepositsTheme.colorsScheme.gradientMainAppBackground
            val scrollState = rememberScrollState(0)

            /* Animate wave line background */
            Box(
                modifier = Modifier.background(Brush.verticalGradient(colors = mainBackgroundColor))
            ) {
                WaveLines(lineColor = DepositsTheme.colorsScheme.brandSecondary)
            }

            /* Main Contain */
            TopAppBar(
                onNotification = {},
                onViewProfile = {},
                scrollState = scrollState
            ) {
                BodyContainer(scrollState = scrollState) {
                    TransparentCard(widthBorder = 0.5.dp, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TransparentCard(widthBorder = 0.5.dp, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TransparentCard(widthBorder = 0.5.dp, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TransparentCard(widthBorder = 0.5.dp, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TransparentCard(widthBorder = 0.5.dp, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                        Text(text = "Hello, World!")
                    }

                }
            }
        }
    }
}

@Preview
@Composable
private fun AppPreview() {
    DepositsTheme {
        DepositsApp()
    }
}