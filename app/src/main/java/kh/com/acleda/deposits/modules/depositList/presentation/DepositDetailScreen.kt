package kh.com.acleda.deposits.modules.depositList.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.components.CenterTopAppBar
import kh.com.acleda.deposits.modules.depositList.presentation.component.DepositDetailHeader
import kh.com.acleda.deposits.modules.depositList.presentation.component.DetailListItem
import kh.com.acleda.deposits.modules.depositList.presentation.component.DetailListItemModel
import kh.com.acleda.deposits.modules.depositList.presentation.component.DetailListItemType
import kh.com.acleda.deposits.modules.depositList.presentation.component.getTermBackgroundColorByCcy
import kh.com.acleda.deposits.modules.depositList.presentation.component.getTermIconById
import kh.com.acleda.deposits.modules.depositList.presentation.component.getTermIndicatorColorById
import kh.com.acleda.deposits.modules.home.data.repository.DepositListRepo
import kh.com.acleda.deposits.modules.home.domain.model.DepositItemModel
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gray2

@Composable
fun DepositDetailScreen(
    modifier: Modifier = Modifier
) {
    val termList = DepositListRepo.getDepositList(LocalContext.current)
    val term: DepositItemModel = termList.listMM.last()

    val convertedListData = convertToDetailList()

    CenterTopAppBar(
        title = "Deposit Detail",
    ) { innerPadding ->
        val state = rememberLazyListState()

        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .background(Gray2.copy(alpha = 0.8f))
                .fillMaxHeight(),
            contentPadding = PaddingValues(bottom = 16.dp),
            state = state,
        ) {
            item {
                DepositDetailHeader(
                    term = term,
                    backgroundColor = { Gray2.copy(alpha = 0.8f) },
                    currencyColor = {
                        getTermBackgroundColorByCcy(term.currency).copy(alpha = 0.8f)
                    },
                    termTypeColor = {
                        getTermIndicatorColorById(term.termTypeId).copy(alpha = 0.8f)
                    },
                    termIcon = {
                        getTermIconById(term.termTypeId)
                    }
                )
            }

            items(convertedListData) { item ->
                DetailListItem(data = item, backgroundColor = Gray2.copy(alpha = 0.8f))
            }
        }
    }
}

fun convertToDetailList(/*term: DepositItemModel*/): List<DetailListItemModel> {
    return listOf(
        DetailListItemModel(
            type = DetailListItemType.DEFAULT,
            title = "Deposit Account:",
            value = "Mario Liza-T"
        ),
        DetailListItemModel(
            type = DetailListItemType.DEFAULT,
            value = "0001-04690-96007-13 (KHR)-T"
        ),
        DetailListItemModel(
            type = DetailListItemType.DEFAULT,
            title = "Deposit Amount:",
            value = "100.00 USD-T"
        ),
        DetailListItemModel(
            type = DetailListItemType.DEFAULT,
            title = "Deposit Term:",
            value = "36 Months-T"
        ),
        DetailListItemModel(
            type = DetailListItemType.DEFAULT,
            title = "Interest Rate:",
            value = "4.25%-T"
        ),
        DetailListItemModel(
            type = DetailListItemType.BREAK_LINE,
        ),
        DetailListItemModel(
            type = DetailListItemType.DEFAULT,
            title = "Effective Date:",
            value = "March 03, 2024-T"
        ),
        DetailListItemModel(
            type = DetailListItemType.DEFAULT,
            title = "Maturity Date:",
            value = "April 03, 2024-T"
        ),
        DetailListItemModel(
            type = DetailListItemType.DEFAULT,
            title = "Auto-Renewal:",
            value = "Renewal with principal-T"
        )
    )
}


@Preview
@Composable
private fun Preview() {
    DepositsTheme{
        DepositDetailScreen()
    }

}