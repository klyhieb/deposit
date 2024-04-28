package kh.com.acleda.deposits.modules.openNewTerm.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import kh.com.acleda.deposits.ui.theme.Blue6
import kh.com.acleda.deposits.ui.theme.Blue7
import kh.com.acleda.deposits.ui.theme.Blue8
import kh.com.acleda.deposits.ui.theme.Gray0
import kh.com.acleda.deposits.ui.theme.Gray3
import kh.com.acleda.deposits.ui.theme.Gray4
import kh.com.acleda.deposits.ui.theme.Gray6

fun ContentDrawScope.drawWithLayer(block: ContentDrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}

data class CurrencyTabModel(
    val ccy: CCY,
    @DrawableRes val icon: Int? = null,
    val text: String? = ""
)

@Composable
fun TextSwitch(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    items: List<CurrencyTabModel>,
    onSelectionChange: (Int, CurrencyTabModel) -> Unit
) {

    BoxWithConstraints(
        modifier
            .height(32.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Gray3)
            .padding(2.dp)
    ) {
        if (items.isNotEmpty()) {

            val maxWidth = this.maxWidth
            val tabWidth = maxWidth / items.size

            val indicatorOffset by animateDpAsState(
                targetValue = tabWidth * selectedIndex,
                animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                label = "indicator offset"
            )

            // This is for shadow layer matching white background
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .shadow(4.dp, RoundedCornerShape(4.dp))
                    .width(tabWidth)
                    .fillMaxHeight()
            )

            Row(modifier = Modifier
                .fillMaxWidth()

                .drawWithContent {

                    // This is for setting black tex while drawing on white background
                    val padding = 4.dp.toPx()
                    drawRoundRect(
                        topLeft = Offset(x = indicatorOffset.toPx() + padding, padding),
                        size = Size(size.width / 2 - padding * 2, size.height - padding * 2),
                        color = Gray3,
                        cornerRadius = CornerRadius(x = 4.dp.toPx(), y = 4.dp.toPx()),
                    )

                    drawWithLayer {
                        drawContent()

                        // This is white top rounded rectangle
                        drawRoundRect(
                            topLeft = Offset(x = indicatorOffset.toPx(), 0f),
                            size = Size(size.width / 2, size.height),
                            color = Blue8,
                            cornerRadius = CornerRadius(x = 4.dp.toPx(), y = 4.dp.toPx()),
                            blendMode = BlendMode.SrcOut
                        )
                    }

                }
            ) {
                items.forEachIndexed { index, tabModel ->
                    Box(
                        modifier = Modifier
                            .width(tabWidth)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null,
                                onClick = {
                                    onSelectionChange(index, tabModel)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row (verticalAlignment = Alignment.CenterVertically) {
                            tabModel.icon?.let {
                                Icon(painterResource(it), contentDescription = null, tint = Gray6, modifier = Modifier.padding(4.dp))
                            }

                            Spacer(Modifier.width(8.dp))

                            tabModel.text?.let {
                                Text(it, style = MaterialTheme.typography.labelLarge, color = Gray6)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TextSwitchTest() {
    val items = remember {
        listOf(
            CurrencyTabModel(CCY.DOLLAR, R.drawable.ic_dollar, "US Dollar"),
            CurrencyTabModel(CCY.RIEL, R.drawable.ic_riel, "Khmer Riel")
        )
    }

    var selectedIndex by remember { mutableStateOf(0) }


    Column {
        TextSwitch(
            selectedIndex = selectedIndex,
            items = items,
            onSelectionChange = { index, _ ->
                selectedIndex = index
            }
        )
    }
}