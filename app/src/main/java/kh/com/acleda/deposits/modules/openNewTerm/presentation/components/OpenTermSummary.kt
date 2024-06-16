package kh.com.acleda.deposits.modules.openNewTerm.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kh.com.acleda.deposits.core.DashLine
import kh.com.acleda.deposits.core.convertDateFormat
import kh.com.acleda.deposits.core.formatAmountWithCcy
import kh.com.acleda.deposits.core.singularPluralWordFormat
import kh.com.acleda.deposits.modules.openNewTerm.domain.model.UnAthOpenTermModel
import kh.com.acleda.deposits.ui.theme.DepositsTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OpenTermSummary(
    modifier: Modifier = Modifier,
    summary: UnAthOpenTermModel,
    textStyle: TextStyle
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
                            style = textStyle,
                            color = DepositsTheme.colors.textSupport
                        )

                        Text(
                            summary.type,
                            style = textStyle,
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
                            style = textStyle,
                            color = DepositsTheme.colors.textSupport
                        )

                        Text(
                            singularPluralWordFormat(summary.term.toString(), "Month"),
                            style = textStyle,
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
                            style = textStyle,
                            color = DepositsTheme.colors.textSupport
                        )

                        Text(
                            summary.principalAmount.toString(),
                            style = textStyle,
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
                            "Interest amount ${singularPluralWordFormat(summary.creditMonths.toString(), "Month")}",
                            style = textStyle,
                            color = DepositsTheme.colors.textSupport
                        )

                        Text(
                            formatAmountWithCcy(summary.interestInCreditMonths, summary.ccy),
                            style = textStyle,
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
                            "Tax ${summary.taxRate}%:",
                            style = textStyle,
                            color = DepositsTheme.colors.textSupport
                        )

                        Text(
                            formatAmountWithCcy(summary.taxAmountInCreditMonth, summary.ccy),
                            style = textStyle,
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
                            style = textStyle,
                            color = DepositsTheme.colors.textSupport
                        )

                        Text(
                            convertDateFormat(summary.effectiveDate),
                            style = textStyle,
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
                            style = textStyle,
                            color = DepositsTheme.colors.textSupport
                        )

                        Text(
                            convertDateFormat(summary.maturityDate),
                            style = textStyle,
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
                            "Final Maturity Date:",
                            style = textStyle,
                            color = DepositsTheme.colors.textSupport
                        )

                        Text(
                            convertDateFormat(summary.finalMaturityDate),
                            style = textStyle,
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
                            "Rollover Time:",
                            style = textStyle,
                            color = DepositsTheme.colors.textSupport
                        )

                        Text(
                            singularPluralWordFormat(summary.rolloverTime.toString(), "Time"),
                            style = textStyle,
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
                            style = textStyle,
                            color = DepositsTheme.colors.textSupport
                        )

                        Text(
                            summary.autoRenewal,
                            style = textStyle,
                            color = DepositsTheme.colors.textHelpLabel
                        )
                    }

                    DashLine(
                        Modifier.padding(top = 8.dp),
                        lineColor = DepositsTheme.colors.textSupport
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            "Received at Final maturity:",
                            style = textStyle,
                            color = DepositsTheme.colors.textSupport
                        )

                        Text(
                            formatAmountWithCcy(summary.totalToReceiveAtFinalMaturity, summary.ccy),
                            style = textStyle,
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
                            "Net Interest ${singularPluralWordFormat(summary.creditMonths.toString(), "Month")}:",
                            style = textStyle.copy(fontSize = 14.sp),
                            color = DepositsTheme.colors.textSupport
                        )

                        Text(
                            formatAmountWithCcy(summary.netInterestInCreditMonths, summary.ccy),
                            style = textStyle,
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
                            "Total Net Interest:",
                            style = textStyle.copy(fontSize = 14.sp),
                            color = DepositsTheme.colors.textSupport
                        )

                        Text(
                            formatAmountWithCcy(summary.totalNetInterest, summary.ccy),
                            style = textStyle,
                            color = DepositsTheme.colors.textHelpLabel
                        )
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        OpenTermSummary(
            summary = UnAthOpenTermModel(),
            textStyle = MaterialTheme.typography.titleSmall.copy(fontSize = 12.sp)
        )
    }
}