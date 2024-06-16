package kh.com.acleda.deposits.modules.openNewTerm.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.components.CenterTopAppBar
import kh.com.acleda.deposits.components.button.BaseButton
import kh.com.acleda.deposits.core.calculate.OpenTermCalculator
import kh.com.acleda.deposits.core.formatAmount
import kh.com.acleda.deposits.core.formatAmountWithCcy
import kh.com.acleda.deposits.core.roundDoubleAmount
import kh.com.acleda.deposits.modules.home.data.repository.AccountListRepo
import kh.com.acleda.deposits.modules.home.data.repository.DepositRateRepo.getDepositRateByTypeAndCcy
import kh.com.acleda.deposits.modules.home.domain.model.SelectAccountModel
import kh.com.acleda.deposits.modules.home.domain.model.TermType
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import kh.com.acleda.deposits.modules.openNewTerm.domain.model.UnAthOpenTermModel
import kh.com.acleda.deposits.modules.openNewTerm.presentation.components.BottomSheetSelectAccount
import kh.com.acleda.deposits.modules.openNewTerm.presentation.components.OpenNewTermInput
import kh.com.acleda.deposits.modules.openNewTerm.presentation.components.OpenTermSummary
import kh.com.acleda.deposits.modules.openNewTerm.presentation.components.RenewalItemModel
import kh.com.acleda.deposits.modules.openNewTerm.presentation.components.TermDescription
import kh.com.acleda.deposits.modules.openNewTerm.presentation.components.TermsAndConditions
import kh.com.acleda.deposits.modules.openNewTerm.presentation.components.getAvailableOptionRenewal
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold6
import kh.com.acleda.deposits.ui.theme.Gray1
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenNewTermScreen(
    modifier: Modifier = Modifier,
    termType: TermType,
    onBackPress: () -> Unit = {},
    onClickDeposit: (UnAthOpenTermModel) -> Unit = {}
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var sourceAccount by remember {  mutableStateOf("") }
    var totalInterest by remember { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    val accountList = AccountListRepo.getAccountOnBottomSheet(context)

    // -----------------------------------------------------------------
    fun getShowAccountNumber(selectAccountModel: SelectAccountModel): String {
        return "${selectAccountModel.accountNumber} (${selectAccountModel.currency.dec.uppercase()})"
    }
    fun getAutoRenewal(renewalItemModel: RenewalItemModel): String {
        return renewalItemModel.value
    }
    // -----------------------------------------------------------------
    var selectedCcy by rememberSaveable { mutableStateOf(CCY.DOLLAR) }
    val rates = getDepositRateByTypeAndCcy(context, termType, selectedCcy)
    val firstRateModel = rates.rateDetails?.firstOrNull()
    val defaultTermMonth = firstRateModel?.term?.toIntOrNull() ?: 0
    val defaultAnnualRate = firstRateModel?.currentPA ?: 0.0

    var showAccountNumber by rememberSaveable { mutableStateOf(getShowAccountNumber(accountList[0])) }
    var depositAmount by rememberSaveable { mutableDoubleStateOf(0.0) }

    val taxRate = 6.00 // 6.00 %
    var termMonths by rememberSaveable { mutableIntStateOf(defaultTermMonth) }
    var annualRate by rememberSaveable { mutableDoubleStateOf(defaultAnnualRate) }
    var renewalTime by remember { mutableIntStateOf(0) } // 0 for default renewal time
    val renewalOptions = rememberSaveable { getAvailableOptionRenewal(termMonths, termType) }
    var autoRenewal by rememberSaveable { mutableStateOf(getAutoRenewal(renewalOptions[0].model)) }

    val calculator = OpenTermCalculator(termType = termType, termMonths = termMonths)
    calculator.checkInvalidAmount(principalAmount = depositAmount, ccy = selectedCcy)

    val maturityDate = calculator.maturityDate()
    val numberOfDays = calculator.numberOfDays(endDate = maturityDate)
    val finalMaturityDate = calculator.finalMaturityDate(renewalTime = renewalTime)

    // credit month
    val interestAmountAtCreditMonth = calculator.interest(principalAmount = depositAmount, annualRate = annualRate, numberOfDays = numberOfDays)
    val taxAmountAtCreditMonth = calculator.tax(interestAmount = interestAmountAtCreditMonth, taxRate = taxRate)
    val netInterestAtCreditMonth = calculator.netInterest(interestAmount = interestAmountAtCreditMonth, taxAmount = taxAmountAtCreditMonth)
    val totalReceivedAtAtCreditMonth = calculator.totalToReceive(principalAmount = depositAmount, netInterest = netInterestAtCreditMonth)

    // Final Maturity Both Normal and Special case (Hi-Growth with Principal & Interest)
    val finalToReceived = calculator.finalPrincipal(
        initialPrincipal = depositAmount,
        renewalOption = autoRenewal,
        renewalTimes = renewalTime,
        annualRate = annualRate,
        taxRate = taxRate,
    )

    totalInterest = formatAmountWithCcy(roundDoubleAmount(finalToReceived.interestWithTax, selectedCcy), selectedCcy.dec.uppercase())


    val unAthOpenTermModel = UnAthOpenTermModel(
        showAccountNumber = showAccountNumber,
        accountName = "Khon Lyhieb",
        type = termType.mName,
        term = termMonths,
        principalAmount = roundDoubleAmount(depositAmount, selectedCcy),
        ccy = selectedCcy.dec.uppercase(),
        taxRate = taxRate.toFloat(),
        creditMonths = calculator.getCreditMonthsByTermType(),
        interestInCreditMonths = roundDoubleAmount(interestAmountAtCreditMonth, selectedCcy),
        taxAmountInCreditMonth = roundDoubleAmount(taxAmountAtCreditMonth, selectedCcy),
        effectiveDate = calculator.getStartDate().toString(),
        maturityDate = maturityDate.toString(),
        finalMaturityDate = finalMaturityDate.toString(),
        rolloverTime = renewalTime,
        autoRenewal = autoRenewal,
        netInterestInCreditMonths = roundDoubleAmount(netInterestAtCreditMonth, selectedCcy),
        totalReceivedAtCreditMonths = roundDoubleAmount(totalReceivedAtAtCreditMonth, selectedCcy),
        totalToReceiveAtFinalMaturity = roundDoubleAmount(finalToReceived.totalToReceive, selectedCcy),
        totalNetInterest = roundDoubleAmount(finalToReceived.netInterest, selectedCcy)
    )

    CenterTopAppBar(
        title = "Open New Term",
        onBackClick = onBackPress
    ) { innerPadding ->

        BottomSheetSelectAccount(
            accountList = accountList,
            showBottomSheet = showBottomSheet,
            sheetState = sheetState,
            onDismiss = {
                showBottomSheet = false
            },
            onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheet = false
                    }
                }
                sourceAccount = "${it.type.name}: ${formatAmount(it.balance)} ${it.currency.dec.uppercase()}"
                showAccountNumber = getShowAccountNumber(it)
            }
        )

        Column(
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            TermDescription(
                title = "${termType.mName} Deposit",
                icon = R.drawable.ic_long_term,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 48.dp)
            )

            OpenNewTermInput(
                modifier = Modifier.padding(horizontal = 16.dp),
                sourceAccount = sourceAccount,
                rates = rates,
                renewalOptionList = renewalOptions,
                totalInterest = totalInterest,
                onSelectSourceAccountClick = {
                    showBottomSheet = true
                },
                onSwitchCurrency = {
                    selectedCcy = it
                },
                onInputAmount = {
                    depositAmount = it.text.toDoubleOrNull() ?: 0.0
                },
                onChooseTerm = {
                    termMonths = it.term?.toIntOrNull() ?: 0
                    annualRate = it.currentPA
                },
                onChooseRenewalOption = {
                    autoRenewal = getAutoRenewal(it)
                    if (it.isRenewal) renewalTime = 1
                },
                onChooseRenewalTime = {
                    renewalTime = it
                }
            )

            // this prevent LaunchAffect from onChooseRenewalTime modify renewalTime
            if (autoRenewal == "No Renewal") renewalTime = 0

            OpenTermSummary(
                modifier = Modifier.padding(horizontal = 16.dp),
                summary = unAthOpenTermModel,
                textStyle = MaterialTheme.typography.titleSmall.copy(fontSize = 12.sp)
            )

            TermsAndConditions(
                onToggleClick = {},
                onTermClick = {}
            )

            BaseButton(
                shape = RectangleShape,
                text = "Deposit",
                textColor = Gray1,
                bodyColor = Gold6,
                onClick = { onClickDeposit(unAthOpenTermModel) }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun OpenNewTermScreenPreview() {
    DepositsTheme {
        OpenNewTermScreen(termType = TermType.HI_INCOME)
    }
}

