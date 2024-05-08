package kh.com.acleda.deposits.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import kh.com.acleda.deposits.components.WaveLines
import kh.com.acleda.deposits.components.horizontalLineChart.HorizontalSumChat
import kh.com.acleda.deposits.modules.home.data.repository.DepositListRepo
import kh.com.acleda.deposits.modules.home.data.repository.DepositRateRepo
import kh.com.acleda.deposits.modules.home.domain.model.TermType
import kh.com.acleda.deposits.modules.home.presentation.components.BodyContainer
import kh.com.acleda.deposits.modules.home.presentation.components.CATOpenNewTerm
import kh.com.acleda.deposits.modules.home.presentation.components.CTA
import kh.com.acleda.deposits.modules.home.presentation.components.MainBalance
import kh.com.acleda.deposits.modules.home.presentation.components.PromoCashWithdrawal
import kh.com.acleda.deposits.modules.home.presentation.components.Section
import kh.com.acleda.deposits.modules.home.presentation.components.TopAppBar
import kh.com.acleda.deposits.modules.home.presentation.components.TotalDepositByCurrency
import kh.com.acleda.deposits.modules.home.presentation.components.TotalDepositByType
import kh.com.acleda.deposits.ui.theme.DepositsTheme

@Composable
fun  DepositsApp() {
    DepositsTheme {
        Box {
            val mainBackgroundColor = DepositsTheme.colors.gradientMainAppBackground
            val navController = rememberNavController()

            /* Animate wave line background */
            Box(
                modifier = Modifier.background(Brush.verticalGradient(colors = mainBackgroundColor))
            ) {
                WaveLines(lineColor = DepositsTheme.colors.brandSecondary)
            }

            DepositNavHost(
                navController = navController
            )
        }
    }
}

@Preview
@Composable
private fun AppPreview() {
    DepositsTheme {
        DepositsApp()
    }
}