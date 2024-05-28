package kh.com.acleda.deposits.modules.openNewTerm.presentation.components

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.modules.home.data.repository.DepositRateRepo
import kh.com.acleda.deposits.modules.home.domain.model.DepositRateDetailsModel
import kh.com.acleda.deposits.modules.home.domain.model.DepositRateObjectModel
import kh.com.acleda.deposits.modules.home.domain.model.TermType
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gray5
import kh.com.acleda.deposits.ui.theme.Gray9

@Composable
fun OpenNewTermInput(
    modifier: Modifier = Modifier,
    context: Context,
    termType: TermType,
    sourceAccount: String,
    rates: DepositRateObjectModel,
    totalInterest: Float,
    onSelectSourceAccountClick: () -> Unit,
    onSwitchCurrency: (CCY) -> Unit,
    onInputAmount: (TextFieldValue) -> Unit,
    onChooseTerm: (DepositRateDetailsModel) -> Unit,
    onChooseRenewalOption: (RenewalItemModel) -> Unit
) {
    val placeHolderText by remember { mutableStateOf("Select source account") }

    var selectedIndex by remember { mutableIntStateOf(0) }
    val currencyList = remember {
        listOf(
            CurrencyTabModel(CCY.DOLLAR, R.drawable.ic_dollar_bg, "US Dollar"),
            CurrencyTabModel(CCY.RIEL, R.drawable.ic_riel_bg, "Khmer Riel")
        )
    }

    var currencyIcon by remember { mutableIntStateOf(R.drawable.ic_dollar_bg) }
    val amount by remember { mutableStateOf(TextFieldValue()) }
    var currentRate by remember { mutableStateOf(rates) }

    var showRenewalTime by remember { mutableStateOf(false) }
    val defaultTermTime = currentRate.rateDetails?.get(0)?.term?.toIntOrNull() ?: 0
    var currentSelectedTermMonth by remember { mutableIntStateOf(defaultTermTime) }
    val renewalTimeList = getRenewalTimeList(currentSelectedTermMonth)

    val renewalOptions = getAvailableOptionRenewal(currentSelectedTermMonth, termType)

    /*-------------------------------------------------------------------------*/
    fun selectionOptionSelected(selectedOption: SelectionOption) {
        renewalOptions.forEach { it.selected = false }
        renewalOptions.find { it.model == selectedOption.model }?.selected = true
    }
    /*-------------------------------------------------------------------------*/

    Surface(
        color = Color.White
    ) {
        Column(modifier = modifier) {
            Spacer(modifier = Modifier.height(24.dp))

            TextFieldSelect(
                leadingIcon = R.drawable.ic_wallet,
                trailingIcon = R.drawable.ic_drop_down,
                valueText = sourceAccount,
                placeHolderText = "",
                labelText = placeHolderText,
                borderColor = Gray5,
                textColor = Gray9,
                supportTextColor = Gray5,
                onClick = {
                    onSelectSourceAccountClick()
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Choose Deposit Currency:",
                style = MaterialTheme.typography.titleMedium,
                color = DepositsTheme.colors.textHelp
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextSwitch(
                selectedIndex = selectedIndex,
                items = currencyList,
                onSelectionChange = { index, tabModel ->
                    selectedIndex = index
                    currencyIcon = tabModel.icon!!
                    currentRate =
                        DepositRateRepo.getDepositRateByTypeAndCcy(
                            context,
                            TermType.LONG_TERM,
                            tabModel.ccy
                        )

                    onSwitchCurrency(tabModel.ccy)
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextFieldInput(
                leadingIcon = currencyIcon,
                valueText = amount,
                labelText = "Amount",
                borderColor = Gray5,
                textColor = Gray9,
                supportTextColor = Gray5,
                onValueChange = {
                    onInputAmount(it)
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Choose Term:",
                style = MaterialTheme.typography.titleMedium,
                color = DepositsTheme.colors.textHelp
            )

            Spacer(modifier = Modifier.height(12.dp))

            TermViewPager(
                modifier = Modifier.fillMaxWidth(),
                rateList = currentRate.rateDetails ?: arrayListOf(),
                onCurrentSelect = { index ->
                    currentRate.rateDetails?.get(index)?.let {
                        currentSelectedTermMonth = it.term?.toIntOrNull() ?: 0
                        onChooseTerm(it)
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Total Interest: $totalInterest ${currencyList[selectedIndex].ccy.dec.uppercase()}",
                style = MaterialTheme.typography.labelMedium,
                color = DepositsTheme.colors.textSupport,
                modifier = Modifier.padding(start = 20.dp)
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                "Choose Renewal option:",
                style = MaterialTheme.typography.titleMedium,
                color = DepositsTheme.colors.textHelp
            )

            Spacer(modifier = Modifier.height(12.dp))

            SingleSelectionList(
                options = renewalOptions,
                onOptionClicked = {
                    showRenewalTime = it.model.isRenewal
                    selectionOptionSelected(it)
                    onChooseRenewalOption(it.model)
                },
                modifier = Modifier.height((renewalOptions.size * 80).dp)
            )

            Column (
                modifier = Modifier
                    .animateContentSize()
                    .height(if (!showRenewalTime) 0.dp else 120.dp)
                    .fillMaxWidth()
            ) {
                RenewalTime(
                    nthList = renewalTimeList,
                    onCurrentSelect = {

                    }
                )
            }
        }
    }
}

private fun getAvailableOptionRenewal(termMonth: Int, termType: TermType): List<SelectionOption> {
    val renewalOptionList = getDefaultRenewalOption(termType)
    if (termMonth != 0) {
        // if can't renewal term Coz it over 29 y or 248 months not allow to renewal
        if (isLongTermDeposit(termType)) {
            return if (RenewalOption.list.isEmpty()) {
                renewalOptionList.filter { it.model.id == "REO1" }
            }
            else {
                renewalOptionList
            }
        }
    }

    return renewalOptionList
}

private fun isLongTermDeposit(termType: TermType) = termType == TermType.LONG_TERM
private fun isNotHiGrowthDeposit(termType: TermType) = termType != TermType.HI_GROWTH

private fun getDefaultRenewalOption(termType: TermType): List<SelectionOption> {
    var renewalOptionList = RenewalOption.list
    // Only Hi_Grow have 3 option (last option) otherwise have 2
    if (isNotHiGrowthDeposit(termType)) {
        renewalOptionList = renewalOptionList.filterNot { it.model.id == "REO3" }
    }

    return renewalOptionList
}

object RenewalOption {
    val list = listOf(
        SelectionOption(
            model = RenewalItemModel(
                id = "REO1",
                title = "No Renewal",
                des = "Principal will be credit to your account on maturity date",
                isRenewal = false
            ),
            initialSelectedValue = false
        ),
        SelectionOption(
            model = RenewalItemModel(
                id = "REO2",
                title = "Renewal with principal",
                des = "Principal will be deposit in new term"
            ),
            initialSelectedValue = false
        ),
        SelectionOption(
            model = RenewalItemModel(
                id = "REO3",
                title = "Renewal with principal & Interest",
                des = "Principal & Interest will be deposit in new term"
            ),
            initialSelectedValue = false
        )
    )
}

@Composable
fun RenewalTime(
    nthList: ArrayList<Int>,
    onCurrentSelect: (Int) -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))

    Text(
        "Choose Renewal times:",
        style = MaterialTheme.typography.titleMedium,
        color = DepositsTheme.colors.textHelp
    )

    Spacer(modifier = Modifier.height(12.dp))

    RenewalTimeViewPager(
        nthList = nthList,
        onCurrentSelect = onCurrentSelect
    )
}