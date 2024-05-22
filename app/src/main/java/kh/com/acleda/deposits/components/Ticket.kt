package kh.com.acleda.deposits.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.components.shape.TicketShape
import kh.com.acleda.deposits.core.DashLine

@Composable
fun Ticket(
    modifier: Modifier = Modifier,
    containerColor: Color,
    middlePercentage: Float,
    contentTop: @Composable ColumnScope.() -> Unit,
    contentBottom: @Composable ColumnScope.() -> Unit
) {
    val density = LocalDensity.current
    var composableWidth by remember { mutableStateOf(0.dp) }
    var composableHeight by remember { mutableStateOf(0.dp) }

    Surface(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                composableWidth = with(density) {
                    coordinates.size.width.toDp()
                }
                composableHeight = with(density) {
                    coordinates.size.height.toDp()
                }
            },
        color = containerColor,
        shape = TicketShape(
            circleVerticalPercentage = middlePercentage,
            circleRadius = 8.dp,
            cornerSize = CornerSize(4.dp)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val verticalLinePercentage = middlePercentage.minus(other = 0.5f)
            DashLine(
                itemWidth = 16f,
                spacing = 16f,
                strokeWidth = 3f,
                modifier = Modifier.offset(y = composableHeight.times(verticalLinePercentage))
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier
                        .weight(middlePercentage)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp)
                ) {
                    contentTop()
                }

                Surface(
                    color = Color.Transparent,
                    modifier = Modifier
                        .weight(1 - middlePercentage)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp)
                ) {
                    contentBottom()
                }
            }
        }
    }
}