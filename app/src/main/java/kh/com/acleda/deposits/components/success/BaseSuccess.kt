package kh.com.acleda.deposits.components.success

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.components.CenterTopAppBar
import kh.com.acleda.deposits.components.button.BaseButton
import kh.com.acleda.deposits.ui.theme.Blue4
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold6
import kh.com.acleda.deposits.ui.theme.Gray1
import kh.com.acleda.deposits.ui.theme.Green3

@Composable
fun BaseSuccess(
    modifier: Modifier = Modifier,
    title: String,
    containerColor: Color = Blue4.copy(0.7f),
    successDescription: String,
    textButton: String,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {

    var isClickable by remember { mutableStateOf(false) }

    CenterTopAppBar(
        title = title,
        hasBackButton = false
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = containerColor,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 24.dp)
                    ) {
                        /* success animate lottie */
                        Box(
                            modifier = modifier
                                .size(150.dp)
                        ) {
                            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.success))
                            val logoAnimationState = animateLottieCompositionAsState(
                                composition = composition,
                                isPlaying = true
                            )
                            LottieAnimation(
                                composition = composition,
                                progress = { logoAnimationState.progress }
                            )

                            if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
                                isClickable = true
                            }
                        }

                        Text(
                            text = successDescription,
                            style = MaterialTheme.typography.titleLarge,
                            color = DepositsTheme.colors.textPrimary,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        /* Content goes here */
                        content()
                    }
                }
            }

            BaseButton(
                enable = isClickable,
                text = textButton,
                shape = RectangleShape,
                textColor = Gray1,
                bodyColor = Gold6,
                onClick = onClick
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        BaseSuccess(
            modifier = Modifier,
            title = "Open New Term",
            successDescription = "Term Stop Renewal was Completed",
            textButton = "view deposit list"
        ) {
            /* content */
            Text(
                text = "Please check your new term in the list.",
                style = MaterialTheme.typography.labelLarge,
                color = Green3,
                textAlign = TextAlign.Center
            )
        }
    }
}