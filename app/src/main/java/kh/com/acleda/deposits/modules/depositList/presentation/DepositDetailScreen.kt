package kh.com.acleda.deposits.modules.depositList.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.components.CenterTopAppBar
import kh.com.acleda.deposits.components.button.BaseButton
import kh.com.acleda.deposits.components.dialog.CloseTermDialog
import kh.com.acleda.deposits.modules.depositList.presentation.component.CornerType
import kh.com.acleda.deposits.modules.depositList.presentation.component.DepositDetailHeader
import kh.com.acleda.deposits.modules.depositList.presentation.component.DetailListItem
import kh.com.acleda.deposits.modules.depositList.presentation.component.DetailListItemModel
import kh.com.acleda.deposits.modules.depositList.presentation.component.DetailListItemType
import kh.com.acleda.deposits.modules.depositList.presentation.component.getTermBackgroundColorByCcy
import kh.com.acleda.deposits.modules.depositList.presentation.component.getTermIconById
import kh.com.acleda.deposits.modules.depositList.presentation.component.getTermIndicatorColorById
import kh.com.acleda.deposits.modules.home.domain.model.DepositItemModel
import kh.com.acleda.deposits.ui.theme.Blue2
import kh.com.acleda.deposits.ui.theme.Gray1
import kh.com.acleda.deposits.ui.theme.Gray2
import kh.com.acleda.deposits.ui.theme.Red5

@Composable
fun DepositDetailScreen(
    modifier: Modifier = Modifier,
    term: DepositItemModel,
    isFromCloseRequest: Boolean = false,
    onBackPress: () -> Unit = {},
    onCloseTermDialogConfirm: (DepositItemModel) -> Unit = {}
) {

    val convertedListData = convertToDetailList()

    CenterTopAppBar(
        title = "Deposit Detail",
        onBackClick = onBackPress
    ) { innerPadding ->
        val state = rememberLazyListState()

        var popupCloseTermDialog by remember { mutableStateOf(false) }

        if (popupCloseTermDialog) {
            CloseTermDialog(
                onDismissRequest = { popupCloseTermDialog = false },
                onConfirm = {
                    popupCloseTermDialog = false
                    onCloseTermDialogConfirm(term)
                }
            )
        }

        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .background(Blue2.copy(alpha = 0.8f))
                .fillMaxHeight(),
            contentPadding = PaddingValues(bottom = 16.dp),
            state = state,
            verticalArrangement = TopWithFooter
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

            item {
                /*Show btn when request Closing Term*/
                if (isFromCloseRequest) {
                    BaseButton(
                        shape = RectangleShape,
                        text = "close term",
                        textColor = Gray1,
                        bodyColor = Red5,
                        onClick = { popupCloseTermDialog = true }
                    )
                }
            }
        }
    }
}

object TopWithFooter : Arrangement.Vertical {
    override fun Density.arrange(totalSize: Int, sizes: IntArray, outPositions: IntArray) {
        var y = 0
        sizes.forEachIndexed { index, size ->
            outPositions[index] = y
            y += size
        }

        if (y < totalSize) {
            val lastIndex = outPositions.lastIndex
            val remainBottomPadding: Int = 14.dp.roundToPx()
            outPositions[lastIndex] = (totalSize + remainBottomPadding) - sizes.last()
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
            cornerType = CornerType.BOTTOM,
            title = "Interest Rate:",
            value = "4.25%-T"
        ),
        DetailListItemModel(
            type = DetailListItemType.BREAK_LINE,
        ),
        DetailListItemModel(
            type = DetailListItemType.DEFAULT,
            cornerType = CornerType.TOP,
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
            title = "Rollover time:",
            value = "2 Times-T"
        ),
        DetailListItemModel(
            type = DetailListItemType.DEFAULT,
            cornerType = CornerType.BOTTOM,
            title = "Auto-Renewal:",
            value = "Renewal with principal-T"
        )
    )
}


@Preview
@Composable
private fun Preview() {
    /*DepositsTheme{
        DepositDetailScreen()
    }*/

}