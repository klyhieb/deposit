package kh.com.acleda.deposits.modules.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.components.TransparentCard
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.md_theme_light_inverseSurface
import kotlin.math.max
import kotlin.math.min

private val GradientScroll = 24.dp
private val ImageOverlap = 32.dp
private val MinTitleOffset = 64.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = ImageOverlap + MinTitleOffset + GradientScroll
private val ExpandedImageSize = 64.dp
private val CollapsedImageSize = 40.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    onNotification: () -> Unit,
    onViewProfile: () -> Unit,
    scrollState: ScrollState,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier
        .fillMaxSize()
        .background(Color.Transparent)) {
        Notification(onNotification = onNotification, modifier = Modifier.align(Alignment.TopEnd))
        content()
        ImageProfile(onViewProfile = onViewProfile) { scrollState.value }
    }
}

@Composable
fun Notification(
    modifier: Modifier = Modifier,
    onNotification: () -> Unit
) {
    IconButton(
        onClick = onNotification,
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
            .background(
                color = md_theme_light_inverseSurface.copy(alpha = 0f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = Icons.Outlined.Notifications,
            tint = Color.White,
            contentDescription = null
        )
    }
}

@Composable
fun ImageProfile(
    modifier: Modifier = Modifier,
    onViewProfile: () -> Unit,
    scrollStateProvider: () -> Int
) {
    val collapseRange = with(LocalDensity.current) { (MaxTitleOffset - MinTitleOffset).toPx() }
    val collapseFractionProvider = { (scrollStateProvider() / collapseRange).coerceIn(0f, 1f) }

    CollapsingProfileLayout(
        collapseFractionProvider = collapseFractionProvider,
        modifier = HzPadding.statusBarsPadding()
    ) {
        Image(
            painter = painterResource(R.drawable.img),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(32.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )

        Column (modifier = Modifier
            .alpha(1f - scrollStateProvider())
            .clickable { onViewProfile() }
        ) {
            Text(text = "Hello, Lyhieb!", style = MaterialTheme.typography.titleLarge, color = Color.White)
            Row {
                Text(text = "See profile", style = MaterialTheme.typography.titleMedium, color = Color.White)
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowRight,
                    tint = Color.White,
                    contentDescription = null
                )
            }

        }
    }
}

@Composable
fun CollapsingProfileLayout(
    collapseFractionProvider: () -> Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) {measurables, constraints ->
        check(measurables.size == 2)

        val collapseFraction = collapseFractionProvider()
        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))

        val textPlaceable = measurables[1].measure(constraints)

        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction)
            .roundToPx()
        val imageX = lerp(0, 0, collapseFraction)

        layout(
            width = constraints.maxWidth,
            height = max(imageY + imageWidth, textPlaceable.height)
        ) {
            imagePlaceable.placeRelative(imageX, imageY)
            val textY = imageY + imageWidth / 2 - textPlaceable.height / 2
            textPlaceable.placeRelative(imageX + imageWidth + 10.dp.roundToPx(), textY)
        }
    }
}
