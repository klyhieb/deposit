package kh.com.acleda.deposits.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.components.TransparentCard
import kh.com.acleda.deposits.components.WaveLines
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
fun DepositsApp() {
    DepositsTheme {
        Box {
            val mainBackgroundColor = DepositsTheme.colors.gradientMainAppBackground
            val scrollState = rememberScrollState(0)

            val summaryTermDeposit = DepositListRepo.getSummaryTermDeposit(LocalContext.current)
            val openNewTermCATData = DepositRateRepo.getCATOpenTerm(LocalContext.current)

            /* Animate wave line background */
            Box(
                modifier = Modifier.background(Brush.verticalGradient(colors = mainBackgroundColor))
            ) {
                WaveLines(lineColor = DepositsTheme.colors.brandSecondary)
            }

            /* Main Contain */
            TopAppBar(
                onNotification = {},
                onViewProfile = {},
                scrollState = scrollState
            ) {
                BodyContainer(scrollState = scrollState) {
                    MainBalance(
                        onViewBalance = {},
                        onAddNewWallet = {}
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    CTA(
                        onWithdraw = {},
                        onDeposit = {},
                        onChangeMainWallet = {}
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Section(label = "Total Deposits") {
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            TotalDepositByType(
                                summaryTermDeposit = summaryTermDeposit,
                                preferStroke = 16.dp,
                                modifier = Modifier
                                    .weight(0.55f)
                                    .height(200.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Column(
                                Modifier
                                    .weight(0.45f)
                                    .fillMaxHeight()
                                    .height(200.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                TotalDepositByCurrency(
                                    summaryTermDeposit = summaryTermDeposit,
                                    termType = TermType.HI_INCOME,
                                    isHasCenterLabels = false,
                                    preferStroke = 8.dp,
                                    modifier = Modifier.size(45.dp)
                                )

                                TotalDepositByCurrency(
                                    summaryTermDeposit = summaryTermDeposit,
                                    termType = TermType.HI_GROWTH,
                                    isHasCenterLabels = false,
                                    preferStroke = 8.dp,
                                    modifier = Modifier.size(45.dp)
                                )
                                TotalDepositByCurrency(
                                    summaryTermDeposit = summaryTermDeposit,
                                    termType = TermType.LONG_TERM,
                                    isHasCenterLabels = false,
                                    preferStroke = 8.dp,
                                    modifier = Modifier.size(45.dp)
                                )
                            }
                        }

                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    
                    Section(label = "Open new Term Deposit") {
                        CATOpenNewTerm(terms = openNewTermCATData)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    PromoCashWithdrawal(onClick = {})

                    Spacer(modifier = Modifier.height(20.dp))

                    Section(label = "My investments") {}

                    Spacer(modifier = Modifier.height(20.dp))

                    Section(label = "Exchange rates") {}
                }
            }
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