package kh.com.acleda.deposits.modules.home.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import kh.com.acleda.deposits.ui.theme.Badge
import kh.com.acleda.deposits.ui.theme.DepositsTheme

@Composable
fun BadgeCurrency(
    modifier: Modifier = Modifier,
    text: String
) {
    Surface(
        color = Badge,
        modifier = modifier.clip(RoundedCornerShape(4.dp))
    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelMedium,
            color = DepositsTheme.colors.textHelpLabel,
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp)
        )
    }
}

@Preview
@Composable
private fun CurrencyBadgePreview() {
    DepositsTheme {
        /*BadgeCurrency(text = "usd")*/

        AnimatedLinearProgressIndicator(
            5f
        )
    }
}


@Composable
fun AnimatedLinearProgressIndicator(
    indicatorProgress: Float,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    var progress by remember { mutableStateOf(0F) }
    val progressAnimDuration = 3_500
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing),
    )
    LinearProgressIndicator(
        progress = { progressAnimation },
        modifier = Modifier
            .fillMaxWidth()
            .height(12.dp)
            .clip(RoundedCornerShape(8.dp)),
    )
    LaunchedEffect(lifecycleOwner) {
        progress = indicatorProgress
    }
}