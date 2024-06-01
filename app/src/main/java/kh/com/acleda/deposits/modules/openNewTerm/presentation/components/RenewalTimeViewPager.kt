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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.components.AnimatedViewPager
import kh.com.acleda.deposits.core.modifier.pagerAnimation
import kh.com.acleda.deposits.core.singularPluralWordFormat
import kh.com.acleda.deposits.ui.theme.Blue1
import kh.com.acleda.deposits.ui.theme.Blue8
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold7
import java.util.Calendar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RenewalTimeViewPager(
    modifier: Modifier = Modifier,
    nthList: ArrayList<Int>,
    onCurrentSelect: (Int) -> Unit
) {
    val mPageWidth = 96.dp
    val mPageHeight = 64.dp

    AnimatedViewPager(
        modifier = modifier,
        pageWidth = mPageWidth,
        pageHeight = mPageHeight,
        items = nthList,
        onCurrentSelect = onCurrentSelect
    ) { pageIndex, pagerState ->
        RenewalTimeItem(
            times = nthList[pageIndex],
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
fun RenewalTimeItem(
    modifier: Modifier = Modifier,
    times: Int,
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

            Text(
                text = times.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = DepositsTheme.colors.textHelpLabel,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f)
                    .padding(vertical = 4.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
                )

            Text(
                text = singularPluralWordFormat(times.toString(), "Time", withNumber = false),
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

/**
 * Its helper function to get the nthList for RenewalTime
 */
fun getRenewalTimeList(termMonth: Int): ArrayList<Int> {
    val maxAllowYearFromNow = 50
    val time = ((maxAllowYearFromNow * 12) / termMonth) - 1 // 12 is Months, and 1 for default term

    val nthList: ArrayList<Int> = ArrayList()

    for (x in 0 until time) {
        val numberTime = x + 1
        nthList.add(numberTime)
    }

    return nthList
}

fun getRenewalTimeListByMaxTime(maxTime: Int): ArrayList<Int> {
    val nthList: ArrayList<Int> = ArrayList()

    for (x in 0 until maxTime) {
        val numberTime = x + 1
        nthList.add(numberTime)
    }

    return nthList
}

private fun getCurrentYear(): String {
    val renewDate = Calendar.getInstance()
    renewDate.add(Calendar.DAY_OF_MONTH, 0)
    return (renewDate[Calendar.YEAR]).toString()
}

@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        val mRateList = getRenewalTimeList(termMonth = 1)

        RenewalTimeViewPager(
            nthList = mRateList,
            onCurrentSelect = {

            }
        )
    }
}
