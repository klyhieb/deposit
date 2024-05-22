package kh.com.acleda.deposits.components.shape

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.ui.theme.Red1


class TicketShape(
    val circleVerticalPercentage: Float,
    private val circleRadius: Dp,
    private val cornerSize: CornerSize
) : Shape {


    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(path = getPath(size, density))
    }

    private fun getPath(size: Size, density: Density): Path {
        val roundedRect = RoundRect(size.toRect(), CornerRadius(cornerSize.toPx(size, density)))
        val roundedRectPath = Path().apply { addRoundRect(roundedRect) }
        return Path.combine(
            operation = PathOperation.Intersect,
            path1 = roundedRectPath,
            path2 = getTicketPath(size, density)
        )
    }

    private fun getTicketPath(size: Size, density: Density): Path {
        val circleVerticalPosition = circleVerticalPercentage / 1 * size.height
        val circleRadiusInPx = with(density) { circleRadius.toPx() }
        return Path().apply {
            reset()
            // Ensure we start drawing line at top left
            moveTo(x = 0F, y = 0F)
            // Draw line to top right
            lineTo(x = size.width, y = 0F)
            // Draw line to middle right
            lineTo(x = size.width, y = circleVerticalPosition)
            // Draw right cutout
            arcTo(
                rect = Rect(
                    left = size.width.minus(circleRadiusInPx),
                    top = circleVerticalPosition - circleRadiusInPx,
                    right = size.width.plus(circleRadiusInPx),
                    bottom = circleVerticalPosition + circleRadiusInPx
                ),
                startAngleDegrees = 270F,
                sweepAngleDegrees = -180F, // Sweep angle adjusted for the correct direction
                forceMoveTo = false
            )
            // Draw line to bottom right
            lineTo(x = size.width, y = size.height)
            // Draw line to bottom left
            lineTo(x = 0F, y = size.height)
            // Draw line to middle left
            lineTo(x = 0F, y = circleVerticalPosition)
            // Draw left cutout
            arcTo(
                rect = Rect(
                    left = 0F.minus(circleRadiusInPx),
                    top = circleVerticalPosition - circleRadiusInPx,
                    right = circleRadiusInPx,
                    bottom = circleVerticalPosition + circleRadiusInPx
                ),
                startAngleDegrees = 90F,
                sweepAngleDegrees = -180F, // Sweep angle adjusted for the correct direction
                forceMoveTo = false
            )
            // Draw line to top left
            lineTo(x = 0F, y = 0F)
        }
    }
}

@Preview
@Composable
private fun TicketShapeDemoPreview() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp),
        color = Red1,
        shape = TicketShape(
            circleVerticalPercentage = 0.3f,
            circleRadius = 10.dp,
            cornerSize = CornerSize(20.dp))
    ) {

    }
}