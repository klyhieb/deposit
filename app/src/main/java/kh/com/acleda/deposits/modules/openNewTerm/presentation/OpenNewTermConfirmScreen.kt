package kh.com.acleda.deposits.modules.openNewTerm.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kh.com.acleda.deposits.components.BadgeWithText
import kh.com.acleda.deposits.components.CenterTopAppBar
import kh.com.acleda.deposits.components.Ticket
import kh.com.acleda.deposits.components.button.BaseButton
import kh.com.acleda.deposits.core.convertDateFormat
import kh.com.acleda.deposits.core.formatAmountWithCcy
import kh.com.acleda.deposits.core.singularPluralWordFormat
import kh.com.acleda.deposits.modules.depositList.presentation.component.DetailListItem
import kh.com.acleda.deposits.modules.depositList.presentation.component.DetailListItemModel
import kh.com.acleda.deposits.modules.openNewTerm.domain.model.UnAthOpenTermModel
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold2
import kh.com.acleda.deposits.ui.theme.Gold6
import kh.com.acleda.deposits.ui.theme.Gray1

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OpenNewTermConfirmScreen(
    modifier: Modifier = Modifier,
    model: UnAthOpenTermModel,
    onBackPress: () -> Unit = {},
    onClickConfirm: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val convertList = convertModelToListDetail(model)

    CenterTopAppBar(
        title = "Open New Term",
        onBackClick = onBackPress
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Ticket(
                    modifier = Modifier
                        .height(530.dp)
                        .padding(horizontal = 24.dp),
                    middlePercentage = 0.18f,
                    contentTop = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            BadgeWithText(text = "Total Net Interest to Receive:")

                            Text(
                                formatAmountWithCcy(model.totalNetInterest, model.ccy),
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = Gold2
                            )
                        }
                    },
                    contentBottom = {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(convertList) { item ->
                                DetailListItem(
                                    data = item,
                                    textStyle = MaterialTheme.typography.titleSmall.copy(fontSize = 12.sp),
                                    backgroundColor = Color.Transparent
                                )
                            }
                        }
                    }
                )
            }

            BaseButton(
                shape = RectangleShape,
                text = "Confirm",
                textColor = Gray1,
                bodyColor = Gold6,
                onClick = onClickConfirm
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertModelToListDetail(model: UnAthOpenTermModel) =
    arrayListOf(
        DetailListItemModel(
            title = "Account:",
            value = model.accountName,
        ),
        DetailListItemModel(
            title = "",
            value = model.showAccountNumber,
            hasLine = true
        ),
        DetailListItemModel(
            title = "Term Type:",
            value = model.type
        ),
        DetailListItemModel(
            title = "Term:",
            value = singularPluralWordFormat(model.term.toString(), "Month")
        ),
        DetailListItemModel(
            title = "Principal amount:",
            value = formatAmountWithCcy(model.principalAmount, model.ccy)
        ),
        DetailListItemModel(
            title = "Interest amount ${singularPluralWordFormat(model.creditMonths.toString(), "Month")}",
            value = formatAmountWithCcy(model.interestInCreditMonths, model.ccy)
        ),
        DetailListItemModel(
            title = "Tax ${"%.2f".format(model.taxRate)}%:",
            value = formatAmountWithCcy(model.taxAmountInCreditMonth, model.ccy)
        ),
        DetailListItemModel(
            title = "Effective date:",
            value = convertDateFormat(model.effectiveDate)
        ),
        DetailListItemModel(
            title = "Maturity date:",
            value = convertDateFormat(model.maturityDate)
        ),
        DetailListItemModel(
            title = "Final Maturity date:",
            value = convertDateFormat(model.finalMaturityDate)
        ),
        DetailListItemModel(
            title = "Rollover time:",
            value = singularPluralWordFormat(model.rolloverTime.toString(), "Time")
        ),
        DetailListItemModel(
            title = "Auto-renewal:",
            value = model.autoRenewal,
            hasLine = true
        ),
        DetailListItemModel(
            title = "Total to received:",
            value = ""
        ),
        DetailListItemModel(
            title = "*at final maturity:",
            value = formatAmountWithCcy(model.totalToReceiveAtFinalMaturity, model.ccy),
            valueColor = Gold2
        ),
        DetailListItemModel(
            title = "Net interest ${singularPluralWordFormat(model.creditMonths.toString(), "Month")}",
            value = formatAmountWithCcy(model.netInterestInCreditMonths, model.ccy),
            valueColor = Gold2
        )
    )

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun TicketShapePreview() {
    DepositsTheme {
        OpenNewTermConfirmScreen(
            model = UnAthOpenTermModel()
        )
    }

}

