package kh.com.acleda.deposits.modules.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.modules.home.domain.model.CATOpenTermModel
import kh.com.acleda.deposits.modules.home.domain.model.CATTermModel
import kh.com.acleda.deposits.modules.home.domain.model.TermType
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Green2
import kh.com.acleda.deposits.ui.theme.Green3
import kh.com.acleda.deposits.ui.theme.Green8
import kh.com.acleda.deposits.ui.theme.Green9

@Composable
fun CATOpenNewTerm(
    modifier: Modifier = Modifier,
    terms: CATOpenTermModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(230.dp)
    ) {
        CATTermType(
            term = terms.longTerm,
            onClick = {},
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.5f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(0.5f)
        ) {
            CATTermType(term = terms.hiGrowth, onClick = {})
            Spacer(modifier = Modifier.weight(1f))
            CATTermType(term = terms.hiIncome, onClick = {})
        }
    }
}

@Composable
fun CATTermType(
    modifier: Modifier = Modifier,
    term: CATTermModel,
    onClick: () -> Unit
) {
    val iconCoverSize = if (term.id == TermType.LONG_TERM.id) 56.dp else 32.dp
    val iconSize = if (term.id == TermType.LONG_TERM.id) 16.dp else 8.dp
    val nameTextStyle = if (term.id == TermType.LONG_TERM.id) MaterialTheme.typography.headlineSmall else MaterialTheme.typography.titleMedium
    val colorTermRateText = if (term.id == TermType.HI_INCOME.id) Green8 else Green3

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = term.mainColor,
        modifier = modifier,
        onClick = onClick
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onClick) {
                    Surface(
                        shape = RoundedCornerShape(100),
                        color = term.supportColor,
                        contentColor = term.iconColor,
                        modifier = Modifier.size(iconCoverSize)
                    ) {
                        Icon(
                            painter = painterResource(term.icon),
                            modifier = Modifier.size(iconSize).padding(8.dp),
                            contentDescription = null,
                        )
                    }
                }

                IconButton(onClick = onClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send),
                        tint = term.iconColor,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = colorTermRateText, fontWeight = FontWeight.W900)) {
                        append("${term.minMonthRate} %")
                    }

                    withStyle(style = SpanStyle(color = DepositsTheme.colors.textPrimary)) {
                        append(" / ${term.minMonth}M")
                    }
                },
                style = MaterialTheme.typography.bodyLarge
            )

            Text(term.name, style = nameTextStyle, color = DepositsTheme.colors.textPrimary)
        }
    }
}