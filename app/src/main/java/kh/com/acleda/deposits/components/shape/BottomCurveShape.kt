package kh.com.acleda.deposits.components.shape

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.ui.theme.Red1

class BottomCurveShape(
    private val cornerSize: CornerSize
) : Shape {

    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        return Outline.Generic(path = getPath(size, density))
    }

    private fun getPath(size: Size, density: Density): Path {
        val roundedRect = RoundRect(size.toRect(), CornerRadius(cornerSize.toPx(size, density)))
        val roundedRectPath = Path().apply { addRoundRect(roundedRect) }
        return Path.combine(operation = PathOperation.Intersect, path1 = roundedRectPath, path2 = getBottomCurvePath(size))
    }

    private fun getBottomCurvePath(size: Size): Path {
        val width = size.width
        val height = size.height

        val middleX = width / 2
        val curveHeight = 100f
        val controlPointOffset = width / 11 // Control point offset for smoothness

        return Path().apply {
            reset()
            moveTo(x = 0F, y = 0F) // Move to starting point

            lineTo(x = width, y = 0F) // Move to top-right

            lineTo(x = width, y = height - curveHeight)

            moveTo(x = 0F, y = height - curveHeight) // Move to starting point
            // First cubic Bézier curve with adjusted control points for smoothness
            cubicTo(controlPointOffset, height, middleX - controlPointOffset, height, middleX, height)
            // Second cubic Bézier curve that mirrors the first one
            cubicTo(middleX + controlPointOffset, height, width - controlPointOffset, height, width, height - curveHeight)

            lineTo(x = 0F, y = 0F)
        }
    }

}

@Composable
fun BottomCurveShapeDemo() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp),
        color = Red1,
        shape = BottomCurveShape(cornerSize = CornerSize(0.dp))
    ) {

    }
}

@Preview
@Composable
private fun BottomCurveShapeDemoPreview() {
    BottomCurveShapeDemo()
}