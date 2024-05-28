package kh.com.acleda.deposits.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.core.modifier.pagerAnimation
import kh.com.acleda.deposits.modules.home.data.repository.DepositRateRepo
import kh.com.acleda.deposits.modules.home.domain.model.DepositRateDetailsModel
import kh.com.acleda.deposits.modules.home.domain.model.TermType
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import kh.com.acleda.deposits.modules.openNewTerm.presentation.components.TermItem
import kh.com.acleda.deposits.ui.theme.DepositsTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun <T> AnimatedViewPager(
    modifier: Modifier = Modifier,
    pageWidth: Dp,
    pageHeight: Dp,
    items: ArrayList<T>,
    onCurrentSelect: (Int) -> Unit,
    itemView: @Composable (pageIndex: Int, pagerState: PagerState) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { items.size },
    )

    var currentPageIndex by remember { mutableIntStateOf(0) }
    val hapticFeedback = LocalHapticFeedback.current
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { currentPage ->
            if (currentPageIndex != currentPage) {
                hapticFeedback.performHapticFeedback(
                    hapticFeedbackType = HapticFeedbackType.LongPress,
                )
                currentPageIndex = currentPage
            }
            // Anything to be triggered by page-change can be done here
            onCurrentSelect(currentPageIndex)
        }
    }

    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = pageWidth + (pageHeight / 2)),
        verticalAlignment = Alignment.CenterVertically,
    ) { pageIndex ->
        /* Actual Composable item for ViewPager */
        itemView(pageIndex, pagerState)
    }
}