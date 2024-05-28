package kh.com.acleda.deposits.modules.openNewTerm.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kh.com.acleda.deposits.components.AnimatedViewPager
import kh.com.acleda.deposits.core.modifier.pagerAnimation
import kh.com.acleda.deposits.modules.home.data.repository.DepositRateRepo
import kh.com.acleda.deposits.modules.home.domain.model.DepositRateDetailsModel
import kh.com.acleda.deposits.modules.home.domain.model.TermType
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import kh.com.acleda.deposits.ui.theme.Blue1
import kh.com.acleda.deposits.ui.theme.Blue8
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold7

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TermViewPager(
    modifier: Modifier = Modifier,
    rateList: ArrayList<DepositRateDetailsModel>,
    onCurrentSelect: (Int) -> Unit
) {
    val mPageWidth = 96.dp
    val mPageHeight = 64.dp

    AnimatedViewPager(
        modifier = modifier,
        pageWidth = mPageWidth,
        pageHeight = mPageHeight,
        items = rateList,
        onCurrentSelect = onCurrentSelect
    ) { pageIndex, pagerState ->
        TermItem(
            termDetail = rateList[pageIndex],
            modifier = Modifier
                .width(mPageWidth + mPageHeight)
                .height(mPageHeight)
                .pagerAnimation(
                    pagerState = pagerState,
                    thisPageIndex = pageIndex
                )
        )
    }
}

@Composable
internal fun TermItem(
    modifier: Modifier = Modifier,
    termDetail: DepositRateDetailsModel
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Blue1
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f)
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = termDetail.term ?: "0",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge,
                    color = DepositsTheme.colors.textHelpLabel,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Text(
                    text = termDetail.month,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall.copy(
                        letterSpacing = (0.5).sp,
                        fontWeight = FontWeight.Normal
                    ),
                    color = DepositsTheme.colors.textHelpLabel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.6f)
                )
            }

            Text(
                text = termDetail.currentPA,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge,
                color = Gold7,
                modifier = Modifier
                    .background(Blue8)
                    .fillMaxWidth()
                    .weight(0.4f)
                    .padding(vertical = 4.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically),
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        val rates = DepositRateRepo.getDepositRateByTypeAndCcy(LocalContext.current, TermType.LONG_TERM, CCY.DOLLAR)
        val mRateList = rates.rateDetails ?: ArrayList()
        TermViewPager(
            rateList = mRateList,
            onCurrentSelect = {

            }
        )
    }
}
