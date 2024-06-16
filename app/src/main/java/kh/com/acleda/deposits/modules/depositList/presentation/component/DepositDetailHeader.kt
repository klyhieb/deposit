package kh.com.acleda.deposits.modules.depositList.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.components.shape.BottomCurveShape
import kh.com.acleda.deposits.modules.depositList.presentation.convertToDetailList
import kh.com.acleda.deposits.modules.home.domain.model.DepositItemModel
import kh.com.acleda.deposits.modules.home.presentation.components.TextBalance
import kh.com.acleda.deposits.ui.theme.Blue0
import kh.com.acleda.deposits.ui.theme.Blue1
import kh.com.acleda.deposits.ui.theme.Blue7
import kh.com.acleda.deposits.ui.theme.Blue9
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gray2
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
                        balance = term.depositAmount.toString(),
                        ccy = getCcyEnum(term.currency),
                        textStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = term.currency,
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = Blue9
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = term.mm,
                    style = MaterialTheme.typography.titleMedium,
                    color = DepositsTheme.colors.textHelp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = term.termTypeName,
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
                .background(termTypeColor())
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
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    backgroundColor: Color
) {
    var corner = RoundedCornerShape(0.dp)
    var extraPadding = PaddingValues()

    when (data.cornerType) {
        CornerType.NON -> { /*use default inited */
        }

        CornerType.TOP -> {
            corner = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
            extraPadding = PaddingValues(top = 8.dp)
        }

        CornerType.BOTTOM -> {
            corner = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
            extraPadding = PaddingValues(bottom = 8.dp)
        }
    }

    when (data.type) {
        DetailListItemType.TITLE -> {
            Text(
                text = data.title,
                style = MaterialTheme.typography.titleMedium,
                color = data.titleColor,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .padding(extraPadding)
            )
        }

        DetailListItemType.ITEM -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(corner)
                    .background(backgroundColor)
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .padding(extraPadding)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = data.title,
                        style = textStyle,
                        color = data.titleColor,
                        maxLines = 1,
                    )

                    Text(
                        text = data.value,
                        style = textStyle,
                        color = data.valueColor,
                        textAlign = TextAlign.End,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )
                }

                if (data.hasLine) {
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 10.dp),
                        color = data.lineColor
                    )
                }
            }
        }

        DetailListItemType.BREAK_LINE -> {
            Spacer(
                modifier = modifier
                    .height(8.dp)
                    .background(Color.Transparent)
            )
        }
    }
}

data class DetailListItemModel(
    val type: DetailListItemType = DetailListItemType.ITEM,
    val cornerType: CornerType = CornerType.NON,
    val title: String = "",
    val value: String = "",
    val titleColor: Color = Blue1, /*Gray6*/
    val valueColor: Color = Blue0, /*Gray9*/
    val lineColor: Color = White.copy(alpha = 0.3f),
    val hasLine: Boolean = false
)

enum class DetailListItemType {
    TITLE, ITEM, BREAK_LINE
}

enum class CornerType {
    NON, TOP, BOTTOM
}