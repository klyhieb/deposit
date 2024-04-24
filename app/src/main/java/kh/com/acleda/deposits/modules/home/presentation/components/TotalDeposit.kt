package kh.com.acleda.deposits.modules.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.components.piechart.PieChart
import kh.com.acleda.deposits.core.TermRow
import kh.com.acleda.deposits.core.safeConvertAccountBalance
import kh.com.acleda.deposits.modules.home.domain.model.DepositListModel
import kh.com.acleda.deposits.modules.home.domain.model.SummaryDepositModel
import kh.com.acleda.deposits.modules.home.domain.model.TermType

/**
 * The Total Deposit summary in HomeScreen.
 */
@Composable
fun TotalDepositByType(
    modifier: Modifier = Modifier,
    summaryTermDeposit: SummaryDepositModel,
    isHasCenterLabels: Boolean = true,
    preferStroke: Dp = 5.dp,
    onAccountClick: (String) -> Unit = {},
) {
    val amountsTotalInRiel = remember {
        summaryTermDeposit.summaryByTypes
            .flatMap { it.totalAmountByCCY }
            .filter { it.ccy == CCY.RIEL }
            .map { it.amount }.sum()
    }

    val amountsTotalInDollar: Float = remember {
        summaryTermDeposit.summaryByTypes
            .flatMap { it.totalAmountByCCY }
            .filter { it.ccy == CCY.DOLLAR }
            .map { it.amount }.sum()
    }

    PieChart(
        modifier = modifier.semantics { contentDescription = "Accounts Screen" },
        items = summaryTermDeposit.summaryInDollarByTypes,
        colors = { summaryInDollarByTypes -> summaryInDollarByTypes.color },
        preferStroke = preferStroke,
        amounts = { summaryInDollarByTypes -> summaryInDollarByTypes.summaryAmountInDollar },
        amountsTotalInRiel = amountsTotalInRiel,
        amountsTotalInDollar = amountsTotalInDollar,
        circleLabel = "",
        isHasCenterLabel = isHasCenterLabels
    )
}

@Composable
fun TotalDepositByCurrency(
    modifier: Modifier = Modifier,
    summaryTermDeposit: SummaryDepositModel,
    termType: TermType,
    isHasCenterLabels: Boolean = true,
    preferStroke: Dp = 5.dp,
    onAccountClick: (String) -> Unit = {},
) {
    val term = summaryTermDeposit.summaryByTypes.single { it.termType == termType }
    val amountsTotalInRiel = remember {
        term.totalAmountByCCY
            .filter { it.ccy == CCY.RIEL }
            .map { it.amount }
            .sum()
    }
    val amountsTotalInDollar = remember {
        term.totalAmountByCCY
            .filter { it.ccy == CCY.DOLLAR }
            .map { it.amount }
            .sum()
    }

    Row (verticalAlignment = Alignment.CenterVertically) {
        PieChart(
            modifier = modifier,
            items = term.totalAmountByCCY,
            colors = { totalAmountByCCY -> totalAmountByCCY.color },
            preferStroke = preferStroke,
            amounts = { totalAmountByCCY -> totalAmountByCCY.proportionAmount },
            amountsTotalInRiel = amountsTotalInRiel,
            amountsTotalInDollar = amountsTotalInDollar,
            circleLabel = "Total-Amount",
            isHasCenterLabel = isHasCenterLabels
        )
        
        Spacer(Modifier.width(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(term.totalAmountByCCY) {
                TermRow(color = it.color, amount = it.amount.toString(), ccy = it.ccy)
            }
        }
    }
}
