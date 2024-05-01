package kh.com.acleda.deposits.modules.openNewTerm.presentation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.components.CenterTopAppBar
import kh.com.acleda.deposits.components.button.SUButton
import kh.com.acleda.deposits.modules.home.data.repository.DepositRateRepo.getDepositRateByTypeAndCcy
import kh.com.acleda.deposits.modules.home.domain.model.TermType
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import kh.com.acleda.deposits.modules.openNewTerm.domain.model.OpenTermDepositModel
import kh.com.acleda.deposits.modules.openNewTerm.presentation.components.OpenNewTermInput
import kh.com.acleda.deposits.modules.openNewTerm.presentation.components.OpenTermSummary
import kh.com.acleda.deposits.modules.openNewTerm.presentation.components.TermDescription
import kh.com.acleda.deposits.modules.openNewTerm.presentation.components.TermsAndConditions
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold6
import kh.com.acleda.deposits.ui.theme.Gray1

@Composable
fun OpenNewTermScreen(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val sourceAccount by remember { mutableStateOf("") }

    val rates = getDepositRateByTypeAndCcy(context, TermType.LONG_TERM, CCY.DOLLAR)

    val totalInterest by remember { mutableFloatStateOf(0.0f) }


    CenterTopAppBar(
        title = "Open New Term",
        onBackClick = { /*TODO*/ }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            TermDescription(
                title = "Long Term Deposit",
                icon = R.drawable.ic_long_term,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 48.dp)
            )

            OpenNewTermInput(
                modifier = Modifier.padding(horizontal = 16.dp),
                context = context,
                sourceAccount = sourceAccount,
                rates = rates,
                totalInterest = totalInterest,
                onSelectSourceAccountClick = {

                },
                onSwitchCurrency = {

                },
                onInputAmount = {
                    Log.e("TAG", "Amount = ${it.text}", )
                },
                onChooseTerm = {

                },
                onChooseRenewalOption = {

                }
            )

            OpenTermSummary(
                modifier = Modifier.padding(horizontal = 16.dp),
                taxDisplay = 6.00f,
                summary = OpenTermDepositModel()
            )

            TermsAndConditions(
                onToggleClick = {},
                onTermClick = {}
            )

            SUButton(
                shape = RectangleShape,
                text = "Deposit",
                textColor = Gray1,
                bodyColor = Gold6
            ) {

            }
        }
    }
}





@Preview(showBackground = true)
@Composable
private fun OpenNewTermScreenPreview() {
    DepositsTheme {
        OpenNewTermScreen()
    }
}

