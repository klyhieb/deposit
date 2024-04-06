package kh.com.acleda.deposits.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun WaveLines(
    modifier: Modifier = Modifier,
    lineColor: Color = DepositsTheme.colorsScheme.brand,
    numberOfWaves: Int = 5,
    strokeWidth: Float = 1f,
    speed: Int = 2000,
    rotationAngle: Float = 45f,
) {
    val transition = rememberInfiniteTransition(label = "")
    val waveOffset = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = speed, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "")

    Canvas(modifier = modifier
        .fillMaxSize()
        .graphicsLayer(rotationZ = rotationAngle)) {
        val waveWidth = size.width / numberOfWaves
        val waveLength = size.height
        val waveCount = (size.width / waveWidth).toInt()

        for (i in 0 until waveCount) {
            val wavePhase = waveOffset.value + i / waveCount.toFloat()
            val startX = i * waveWidth

            drawPath(
                color = lineColor,
                path = Path().apply {
                    moveTo(startX, -waveLength)

                    for (y in 0..size.height.toInt() step 20) {
                        val x = startX + waveWidth / 2 * (1 + sin(2 * PI * (y / waveLength + wavePhase)))
                        lineTo(x.toFloat(), y.toFloat())
                    }
                },
                style = Stroke(width = strokeWidth)
            )
        }
    }
}