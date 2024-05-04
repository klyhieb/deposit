package kh.com.acleda.deposits.modules.depositList.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.components.CenterTopAppBar
import kh.com.acleda.deposits.modules.depositList.presentation.component.DepositDateHeader
import kh.com.acleda.deposits.modules.depositList.presentation.component.DepositItem
import kh.com.acleda.deposits.modules.depositList.presentation.component.SummaryDepositList
import kh.com.acleda.deposits.modules.home.data.repository.DepositListRepo
import kh.com.acleda.deposits.ui.theme.DepositsTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DepositListScreen(
    modifier: Modifier = Modifier,

) {
    val summaryTermDeposit = DepositListRepo.getSummaryTermDeposit(LocalContext.current)

    val depositList = DepositListRepo.getDepositList(LocalContext.current)
    val listGroupByDate = depositList.listMM.groupBy { it.ValueDateOri }

    CenterTopAppBar(
        title = "Deposit List",
        action = {
            IconButton(onClick = {/*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = DepositsTheme.colors.textPrimary
                )
            }
        }
    ) { innerPadding ->
        val state = rememberLazyListState()

        LazyColumn(
            modifier = modifier.padding(innerPadding).padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            state = state
        ) {
            item {
                SummaryDepositList(
                    modifier = Modifier.height(210.dp),
                    summaryTermDeposit = summaryTermDeposit,
                    onClick = {}
                )
            }

            listGroupByDate.forEach { (date, items) ->
                stickyHeader {
                    DepositDateHeader(date = date.toString())
                }
                items(items) { item ->
                    DepositItem(
                        term = item,
                        onClick = {},
                        onOptionClick = {}
                    )
                }
            }
        }

    }
}


@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        DepositListScreen()
    }
}