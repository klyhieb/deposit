package kh.com.acleda.deposits.modules.stopRenewal.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import kh.com.acleda.deposits.components.BadgeWithText
import kh.com.acleda.deposits.components.CenterTopAppBar
import kh.com.acleda.deposits.components.Ticket
import kh.com.acleda.deposits.components.button.BaseButton
import kh.com.acleda.deposits.core.convertDateFormat
import kh.com.acleda.deposits.core.singularPluralWordFormat
import kh.com.acleda.deposits.modules.depositList.presentation.component.DetailListItem
import kh.com.acleda.deposits.modules.depositList.presentation.component.DetailListItemModel
import kh.com.acleda.deposits.modules.depositList.presentation.component.getCcyEnum
import kh.com.acleda.deposits.modules.depositList.presentation.component.getTermTypeEnum
import kh.com.acleda.deposits.modules.home.presentation.components.TextBalance
import kh.com.acleda.deposits.modules.stopRenewal.domain.model.StopRenewalConfirmModel
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold1
import kh.com.acleda.deposits.ui.theme.Gold2
import kh.com.acleda.deposits.ui.theme.Gold6
import kh.com.acleda.deposits.ui.theme.Gray1
import kh.com.acleda.deposits.ui.theme.White

@Composable
fun StopRenewalConfirmScreen(
    modifier: Modifier = Modifier,
    model: StopRenewalConfirmModel,
    onBackPress: () -> Unit = {},
    onConfirmClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val convertList = convertModelToListDetail(model)
    val ccyTextStyle = MaterialTheme.typography.headlineSmall.copy(
        fontWeight = FontWeight.Bold
    )

    CenterTopAppBar(
        title = "Stop Renewal",
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
                        .height(400.dp)
                        .padding(horizontal = 24.dp),
                    middlePercentage = 0.3f,
                    contentTop = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            BadgeWithText(text = "Deposit amount:")

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextBalance(
                                    balance = model.depositAmount,
                                    ccy = getCcyEnum(model.ccy),
                                    textStyle = ccyTextStyle,
                                    decimalPartColor = Gold2,
                                    floatingPartColor = Gold1
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = model.ccy,
                                    style = ccyTextStyle,
                                    color = White
                                )
                            }
                        }
                    },
                    contentBottom = {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(convertList) { item ->
                                DetailListItem(
                                    data = item,
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
                onClick = onConfirmClick
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertModelToListDetail(model: StopRenewalConfirmModel) =
    arrayListOf(
        DetailListItemModel(
            title = "Term#:",
            value = model.mmNumber,
            hasLine = true
        ),
        DetailListItemModel(
            title = "Deposit Type:",
            value = getTermTypeEnum(model.depositTypeId).mName,
        ),
        DetailListItemModel(
            title = "Deposit Term:",
            value = singularPluralWordFormat(model.depositTerm, "Month"),
        ),
        DetailListItemModel(
            title = "Renewal:",
            value = model.autoRenewal,
        ),
        DetailListItemModel(
            title = "Rollover time:",
            value = singularPluralWordFormat(model.rolloverTime, "Time"),
        ),
        DetailListItemModel(
            title = "Maturity data:",
            value = convertDateFormat(model.maturityDate),
            hasLine = true
        ),
        DetailListItemModel(
            title = "Rollover time*:",
            value = singularPluralWordFormat(model.newRolloverTime, "Time"),
            valueColor = Gold2
        ),
        DetailListItemModel(
            title = "Maturity data*:",
            value = convertDateFormat(model.newMaturityDate),
            valueColor = Gold2
        )
    )

@Preview
@Composable
fun TicketShapePreview() {
    DepositsTheme {
        val testModel = StopRenewalConfirmModel(
            depositAmount = "400000",
            ccy = "KHR",
            mmNumber = "MM2331400003",
            depositTypeId = "21011",
            depositTerm = "36",
            autoRenewal = "Renewal with principal",
            rolloverTime = "12",
            maturityDate = "April 03, 2026",
            newRolloverTime = "0",
            newMaturityDate = "April 03, 2024",
        )

        StopRenewalConfirmScreen(
            model = testModel
        )
    }
}