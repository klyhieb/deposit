package kh.com.acleda.deposits.modules.openNewTerm.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.core.DashLine
import kh.com.acleda.deposits.modules.openNewTerm.domain.model.OpenTermDepositModel
import kh.com.acleda.deposits.ui.theme.DepositsTheme

@Composable
fun OpenTermSummary(
    modifier: Modifier = Modifier,
    taxDisplay: Float,
    summary: OpenTermDepositModel
) {

    Surface(
        color = Color.White
    ) {
        Column (modifier = modifier) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Summary",
                style = MaterialTheme.typography.titleMedium,
                color = DepositsTheme.colors.brandSecondary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                color = Color(0xFFFFFCF2),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            "Deposit Type:",
                            style = MaterialTheme.typography.titleSmall,
                            color = DepositsTheme.colors.textSecondary
                        )

                        Text(
                            summary.type!!,
                            style = MaterialTheme.typography.titleSmall,
                            color = DepositsTheme.colors.textHelpLabel
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            "Deposit Term:",
                            style = MaterialTheme.typography.titleSmall,
                            color = DepositsTheme.colors.textSecondary
                        )

                        Text(
                            summary.term.toString(),
                            style = MaterialTheme.typography.titleSmall,
                            color = DepositsTheme.colors.textHelpLabel
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            "Deposit Amount:",
                            style = MaterialTheme.typography.titleSmall,
                            color = DepositsTheme.colors.textSecondary
                        )

                        Text(
                            summary.amount.toString(),
                            style = MaterialTheme.typography.titleSmall,
                            color = DepositsTheme.colors.textHelpLabel
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            "Interest Rate",
                            style = MaterialTheme.typography.titleSmall,
                            color = DepositsTheme.colors.textSecondary
                        )

                        Text(
                            summary.interestRate.toString(),
                            style = MaterialTheme.typography.titleSmall,
                            color = DepositsTheme.colors.textHelpLabel
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            "Interest Amount",
                            style = MaterialTheme.typography.titleSmall,
                            color = DepositsTheme.colors.textSecondary
                        )

                        Text(
                            summary.interestRateAmount.toString(),
                            style = MaterialTheme.typography.titleSmall,
                            color = DepositsTheme.colors.textHelpLabel
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            "Tax $taxDisplay%:",
                            style = MaterialTheme.typography.titleSmall,
                            color = DepositsTheme.colors.textSecondary
                        )

                        Text(
                            summary.tax.toString(),
                            style = MaterialTheme.typography.titleSmall,
                            color = DepositsTheme.colors.textHelpLabel
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            "Effective Date:",
                            style = MaterialTheme.typography.titleSmall,
                            color = DepositsTheme.colors.textSecondary
                        )

                        Text(
                            summary.effectiveDate!!,
                            style = MaterialTheme.typography.titleSmall,
                            color = DepositsTheme.colors.textHelpLabel
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            "Maturity Date:",
                            style = MaterialTheme.typography.titleSmall,
                            color = DepositsTheme.colors.textSecondary
                        )

                        Text(
                            summary.maturityDate!!,
                            style = MaterialTheme.typography.titleSmall,
                            color = DepositsTheme.colors.textHelpLabel
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            "Auto-Renewal:",
                            style = MaterialTheme.typography.titleSmall,
                            color = DepositsTheme.colors.textSecondary
                        )

                        Text(
                            summary.autoRenewal!!,
                            style = MaterialTheme.typography.titleSmall,
                            color = DepositsTheme.colors.textHelpLabel
                        )
                    }

                    DashLine(
                        modifier.padding(top = 8.dp),
                        lineColor = DepositsTheme.colors.textSecondary
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    ) {
                        Text(
                            "Total to Receive:",
                            style = MaterialTheme.typography.titleMedium,
                            color = DepositsTheme.colors.textSecondary
                        )

                        Text(
                            summary.totalToReceive.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            color = DepositsTheme.colors.textHelpLabel
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        OpenTermSummary(
            taxDisplay = 6.00f,
            summary = OpenTermDepositModel()
        )
    }
}