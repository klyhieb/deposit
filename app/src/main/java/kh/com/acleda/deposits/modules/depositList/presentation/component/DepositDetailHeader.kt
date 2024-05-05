package kh.com.acleda.deposits.modules.depositList.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.components.shape.BottomCurveShape
import kh.com.acleda.deposits.modules.home.data.repository.DepositListRepo
import kh.com.acleda.deposits.modules.home.domain.model.DepositItemModel
import kh.com.acleda.deposits.modules.home.presentation.components.TextBalance
import kh.com.acleda.deposits.ui.theme.Blue7
import kh.com.acleda.deposits.ui.theme.Blue9
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Red10
import kh.com.acleda.deposits.ui.theme.White

@Composable
fun DepositDetailHeader(
    modifier: Modifier = Modifier,
    term: DepositItemModel,
    backgroundColor: () -> Color,
    currencyColor: () -> Color,
    termTypeColor: () -> Color,
    termIcon: () -> Int
) {

    Box(
        modifier = modifier.background(backgroundColor())
    ) {
        Surface(
            modifier = Modifier
                .height(238.dp)
                .fillMaxWidth()
                .padding(bottom = 54.dp),
            color = currencyColor(),
            shape = BottomCurveShape(cornerSize = CornerSize(0.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text(
                    text = "Total to Receive",
                    style = MaterialTheme.typography.titleSmall,
                    color = DepositsTheme.colors.textSupport
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextBalance(
                        decimalPartColor = Blue9,
                        floatingPartColor = Blue7,
                        balance = term.AmountOri.toString(),
                        ccy = getCcyEnum(term.currency),
                        textStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = term.currency ?: "",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = Blue9
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = term.mm ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    color = DepositsTheme.colors.textHelp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = term.termTypeName ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    color = DepositsTheme.colors.textHelp
                )
            }
        }

        // Circle with the icon
        Surface(
            shape = CircleShape,
            color = termTypeColor(),
            modifier = Modifier
                .size(76.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-20).dp)
                .border(width = 2.dp, color = White, shape = CircleShape)
                .shadow(elevation = 3.dp, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(termIcon()),
                contentDescription = null,
                tint = DepositsTheme.colors.iconInteractive,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun DetailListItem(
    modifier: Modifier = Modifier,
    data: DetailListItemModel,
    backgroundColor: Color
) {
    when (data.type)
    {
        DetailListItemType.DEFAULT ->
        {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .background(backgroundColor)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = DepositsTheme.colors.textSupport
                )

                Text(
                    text = data.value,
                    style = MaterialTheme.typography.titleSmall,
                    color = DepositsTheme.colors.textHelp,
                    textAlign = TextAlign.End
                )
            } 
        }

        DetailListItemType.BREAK_LINE ->
        {
            Spacer(modifier = modifier.height(4.dp).background(Red10))
        }
    }
}

data class DetailListItemModel(
    val type: DetailListItemType,
    val title: String = "",
    val value: String = ""
)

enum class DetailListItemType {
    DEFAULT, BREAK_LINE
}



@Preview
@Composable
private fun Preview() {
    DepositsTheme {

        val termList = DepositListRepo.getDepositList(LocalContext.current)
        val term: DepositItemModel = termList.listMM.last()

        DepositDetailHeader(
            term = term,
            backgroundColor = { Red10 },
            currencyColor = {
                getTermBackgroundColorByCcy(term.currency)
            },
            termTypeColor = {
                getTermIndicatorColorById(term.termTypeId)
            },
            termIcon = {
                getTermIconById(term.termTypeId)
            }
        )
    }
}