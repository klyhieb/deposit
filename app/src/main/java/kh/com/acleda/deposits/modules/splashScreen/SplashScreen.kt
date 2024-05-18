package kh.com.acleda.deposits.modules.splashScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.ui.SplashScreen
import kh.com.acleda.deposits.ui.theme.DepositsTheme

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onSplashScreenFinish: () -> Unit
) {
    val mainBackgroundColor = DepositsTheme.colors.gradientMainAppBackground

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = mainBackgroundColor))
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.start_animate_logo))
        val logoAnimationState = animateLottieCompositionAsState(composition = composition)
        LottieAnimation(
            composition = composition,
            progress = { logoAnimationState.progress }
        )
        if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
            onSplashScreenFinish()
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        SplashScreen() {
        }
    }
}