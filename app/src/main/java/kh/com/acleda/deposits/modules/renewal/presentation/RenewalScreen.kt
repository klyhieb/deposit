package kh.com.acleda.deposits.modules.renewal.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.components.CenterTopAppBar
import kh.com.acleda.deposits.components.button.BaseButton
import kh.com.acleda.deposits.core.calculate.RenewalCalculator
import kh.com.acleda.deposits.core.convertDateFormat
import kh.com.acleda.deposits.core.roundDoubleAmount
import kh.com.acleda.deposits.core.singularPluralWordFormat
import kh.com.acleda.deposits.modules.depositList.presentation.component.CornerType
import kh.com.acleda.deposits.modules.depositList.presentation.component.DetailListItem
import kh.com.acleda.deposits.modules.depositList.presentation.component.DetailListItemModel
import kh.com.acleda.deposits.modules.depositList.presentation.component.DetailListItemType
import kh.com.acleda.deposits.modules.depositList.presentation.component.getCcyEnum
import kh.com.acleda.deposits.modules.depositList.presentation.component.getTermBackgroundColorByCcy
import kh.com.acleda.deposits.modules.home.domain.model.DepositItemModel
import kh.com.acleda.deposits.modules.home.domain.model.TermType
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import kh.com.acleda.deposits.modules.home.presentation.components.TextBalance
import kh.com.acleda.deposits.modules.openNewTerm.presentation.components.RenewalItemModel
import kh.com.acleda.deposits.modules.openNewTerm.presentation.components.RenewalOption
import kh.com.acleda.deposits.modules.openNewTerm.presentation.components.RenewalTime
import kh.com.acleda.deposits.modules.openNewTerm.presentation.components.SelectionOption
import kh.com.acleda.deposits.modules.openNewTerm.presentation.components.SingleSelectionList
import kh.com.acleda.deposits.modules.openNewTerm.presentation.components.getRenewalTimeListByMaxTime
import kh.com.acleda.deposits.modules.renewal.domain.model.UnAuthRenewalModel
import kh.com.acleda.deposits.ui.theme.Blue7
import kh.com.acleda.deposits.ui.theme.Blue9
import kh.com.acleda.deposits.ui.theme.Gold6
import kh.com.acleda.deposits.ui.theme.Gold7
import kh.com.acleda.deposits.ui.theme.Gray1
import kh.com.acleda.deposits.ui.theme.Gray10
import kh.com.acleda.deposits.ui.theme.Gray6
import kh.com.acleda.deposits.ui.theme.Gray9
import kh.com.acleda.deposits.ui.theme.White

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RenewalScreen(
    modifier: Modifier = Modifier,
    model: DepositItemModel,
    onBackPress: () -> Unit,
    onRenewalClick: (UnAuthRenewalModel) -> Unit
) {
    val termDetailList = convertToTermDetailList(model)
    val renewalOptions = rememberSaveable { mutableStateOf(getRenewalOption()) }
    val calculator = RenewalCalculator()
    val unAuthModel= UnAuthRenewalModel()

    var totalInterest = 0.0
    var taxAmount = 0.0
    var netMonthlyInterest = 0.0
    var totalToReceive = 0.0

    val taxRate = 6.0

    /*-------------------------------------------------------------------------------------------------------------------*/
    // auto select only with this condition
    fun showSelectRenewalOption(): Boolean = model.isNeverRenewal == "Y" && (model.termId) == TermType.HI_GROWTH.id
    /*-------------------------------------------------------------------------------------------------------------------*/

    CenterTopAppBar(
        title = "Renewal",
        onBackClick = onBackPress
    ) { innerPadding ->

        val state = rememberLazyListState()
        val background = Color(0xFFEEE9E9).copy(alpha = 0.5f)

        var renewalTime by remember { mutableIntStateOf(1) }  // 1 for default select renewal time
        val mNewMaturityDate = calculator.calculateNewMaturityDate(
            originalDate = model.maturityDate,
            renewalCount = renewalTime,
            termMonths = model.depositTerm.toLongOrNull() ?: 0
        )
        val modelCcy = getCcyEnum(model.currency)

        /*-------------------------------------------------------------------------------------------------------------------*/
        fun calculations() {
            val amountAfterRenewals = calculator.calculateAmountAfterRenewals(
                principal = model.depositAmount.toDouble(),
                annualRate = model.interestRate.toFloatOrNull() ?: 0.0f,
                renewalTime = renewalTime,
                termMonths = model.depositTerm.toIntOrNull() ?: 0
            )

            totalInterest = calculator.calculateTotalInterest(
                principal = model.depositAmount.toDouble(),
                amountAfterRenewals = amountAfterRenewals
            )

            taxAmount = calculator.calculateTax(
                totalInterest = totalInterest,
                taxRate = taxRate
            )

            val netInterestAfterTax = totalInterest - taxAmount
            val totalMonths = (model.depositTerm.toIntOrNull() ?: 0).times(other = renewalTime)
            netMonthlyInterest = calculator.calculateNetMonthlyInterest(
                netInterestAfterTax = netInterestAfterTax,
                totalMonths = totalMonths
            )

            totalToReceive = calculator.calculateTotalToReceive(
                principal = model.depositAmount.toDouble(),
                netInterestAfterTax = netInterestAfterTax
            )
        }

        fun setModelData() {
            unAuthModel.apply {
                depositAmount = model.depositAmount.toDouble()
                ccy = model.currency
                mm = model.mm
                depositType = model.termName
                depositTerm = model.depositTerm.toIntOrNull() ?: 0
                rolloverTime = model.rolloverTime.toIntOrNull() ?: 0
                maturityDate = model.maturityDate
                autoRenewal = model.autoRenewal

                // fields has effect update
                newRolloverTime = renewalTime + (model.rolloverTime.toIntOrNull() ?: 0)
                newMaturityDate = mNewMaturityDate
                newTotalInterest = roundDoubleAmount(amount = totalInterest, ccy = modelCcy)
                newTax = roundDoubleAmount(amount = taxAmount, ccy = modelCcy)
                newNetMonthlyInterest = roundDoubleAmount(amount = netMonthlyInterest, ccy = modelCcy)
                newTotalToReceiveAtFinalMaturity = roundDoubleAmount(amount = totalToReceive, ccy = modelCcy)
            }
        }
        /*-------------------------------------------------------------------------------------------------------------------*/

        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .background(background.copy(alpha = 0.8f))
                .fillMaxHeight(),
            state = state
        ) {
            item {
                RenewalHeader(model = model)
            }

            items(termDetailList) { item ->
                DetailListItem(data = item, backgroundColor = White.copy(alpha = 0.8f))
            }

            item {
                Rollover(
                    renewalOptions = renewalOptions.value,
                    maxRenewalTime = model.maxRenewalTime.toIntOrNull() ?: 1,
                    showRenewalOption = showSelectRenewalOption(),
                    newMaturityDate = convertDateFormat(mNewMaturityDate),
                    onChooseRenewalOption = {/*TODO*/},
                    onCurrentRenewalTimeSelect = {
                        renewalTime = it + 1 // plus 1 to its index
                    }
                )
            }

            item {
                BaseButton(
                    shape = RectangleShape,
                    text = "renewal",
                    textColor = Gray1,
                    bodyColor = Gold6,
                    onClick = {
                        calculations()
                        setModelData()
                        onRenewalClick(unAuthModel)
                    }
                )
            }
        }
    }
}

/* 'No Renewal' (option) can't be here */
private fun getRenewalOption(): List<SelectionOption> {
    val renewalList = RenewalOption.getList().filter { it.model.id != "REO1" }
    renewalList[0].selected = true // set select for default
    return renewalList
}

@Composable
fun RenewalHeader(
    modifier: Modifier = Modifier,
    model: DepositItemModel
) {
    val icon = getRenewalIconByCcy(model.currency)
    val color = getTermBackgroundColorByCcy(model.currency)
    val ccyTextStyle = MaterialTheme.typography.headlineMedium.copy(
        fontWeight = FontWeight.Bold
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {
        Icon(painterResource(icon), contentDescription = null, tint = color)

        Spacer(modifier = Modifier.height(24.dp))

        Surface(
            shape = RoundedCornerShape(8.dp),
            color = color,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = model.termName,
                    style = MaterialTheme.typography.titleMedium,
                    color = Gray9
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = model.mm,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Gray9
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Deposit amount:",
                    style = MaterialTheme.typography.titleSmall,
                    color = Gray6
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextBalance(
                        balance = model.depositAmount.toString(),
                        ccy = getCcyEnum(model.currency),
                        textStyle = ccyTextStyle,
                        decimalPartColor = Blue9,
                        floatingPartColor = Blue7
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = model.currency,
                        style = ccyTextStyle,
                        color = Blue9
                    )
                }
            }
        }
    }
}

@Composable
fun Rollover(
    modifier: Modifier = Modifier,
    renewalOptions: List<SelectionOption> = arrayListOf(),
    maxRenewalTime: Int,
    showRenewalOption: Boolean,
    newMaturityDate: String,
    onChooseRenewalOption: (RenewalItemModel) -> Unit,
    onCurrentRenewalTimeSelect: (Int) -> Unit
) {

    val lineColor = Color(0xFFAFAFAF)
    val textStyle = MaterialTheme.typography.titleSmall
    val renewalTimeList = getRenewalTimeListByMaxTime(maxRenewalTime)

    fun selectionOptionSelected(selectedOption: SelectionOption) {
        renewalOptions.forEach { it.selected = false }
        renewalOptions.find { it.model == selectedOption.model }?.selected = true
    }

    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Rollover",
            style = MaterialTheme.typography.titleMedium,
            color = Gray10,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )

        Surface(
            shape = RoundedCornerShape(8.dp),
            color = White.copy(alpha = 0.8f),
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                if (showRenewalOption) {
                    SingleSelectionList(
                        options = renewalOptions,
                        onOptionClicked = {
                            selectionOptionSelected(it)
                            onChooseRenewalOption(it.model)
                        },
                        modifier = Modifier.height((renewalOptions.size * 80).dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                HorizontalDivider(
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = lineColor
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "New maturity date:",
                            style = textStyle,
                            color = Gray6,
                            maxLines = 1,
                        )

                        Text(
                            text = newMaturityDate,
                            style = textStyle,
                            color = Gold7,
                            textAlign = TextAlign.End,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(top = 8.dp),
                    color = lineColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                RenewalTime(
                    nthList = renewalTimeList,
                    isVisible = false,
                    onCurrentSelect = onCurrentRenewalTimeSelect
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

private fun getRenewalIconByCcy(ccy: String) =
    when (getCcyEnum(ccy)) {
        CCY.RIEL -> R.drawable.ic_renewal_khr
        CCY.DOLLAR -> R.drawable.ic_renewal_usd
        CCY.DEFAULT -> R.drawable.ic_renewal
    }

@RequiresApi(Build.VERSION_CODES.O)
fun convertToTermDetailList(model: DepositItemModel) =
    arrayListOf(
        DetailListItemModel(
            type = DetailListItemType.TITLE,
            title = "Term Detail",
            titleColor = Gray10
        ),
        DetailListItemModel(
            cornerType = CornerType.TOP,
            title = "Deposit Account:",
            value = model.depositAccountName,
            titleColor = Gray6,
            valueColor = Gray9
        ),
        DetailListItemModel(
            title = "",
            value = model.depositAccountNumber,
            titleColor = Gray6,
            valueColor = Gray9
        ),
        DetailListItemModel(
            title = "Deposit Term:",
            value = singularPluralWordFormat(model.depositTerm, "Month"),
            titleColor = Gray6,
            valueColor = Gray9
        ),
        DetailListItemModel(
            title = "Interest Rate:",
            value = "${model.interestRate}%",
            titleColor = Gray6,
            valueColor = Gray9
        ),
        DetailListItemModel(
            title = "Auto-Renewal:",
            value = model.autoRenewal,
            hasLine = true,
            lineColor = Color(0xFFCECCCC),
            titleColor = Gray6,
            valueColor = Gray9
        ),
        DetailListItemModel(
            title = "Rollover time:",
            value = singularPluralWordFormat(model.rolloverTime, "Time"),
            titleColor = Gray6,
            valueColor = Gray9
        ),
        DetailListItemModel(
            cornerType = CornerType.BOTTOM,
            title = "Maturity data:",
            value = convertDateFormat(model.maturityDate),
            titleColor = Gray6,
            valueColor = Gray9
        )
    )
