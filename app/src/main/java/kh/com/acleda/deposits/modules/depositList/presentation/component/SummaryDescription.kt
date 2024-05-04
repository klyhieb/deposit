package kh.com.acleda.deposits.modules.depositList.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.modules.home.data.repository.DepositListRepo
import kh.com.acleda.deposits.ui.theme.DepositsTheme

@Composable
fun <T> SummaryDescription(
    modifier: Modifier = Modifier,
    title: String,
    maxWidth: Dp,
    data: List<T>,
    color: (T) -> Color,
    name: (T) -> String
) {
    Column (modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = DepositsTheme.colors.textPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))

        LazyVerticalGrid(columns = GridCells.Adaptive(maxWidth),
            horizontalArrangement = Arrangement.Start) {
            items(data) { item: T ->
                DescriptionItem(color = color(item), name = name(item))
            }
        }
    }
}

@Composable
fun DescriptionItem(
    color: Color,
    name: String
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 6.dp)
    ) {
        Spacer(
            modifier = Modifier
                .size(8.dp)
                .background(color)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            color = DepositsTheme.colors.textPrimary
        )
    }
}

@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        val summaryTermDeposit = DepositListRepo.getSummaryTermDeposit(LocalContext.current)

        SummaryDescription(
            modifier = Modifier.width(300.dp),
            title = "By Term Type: ",
            maxWidth = 80.dp,
            data = summaryTermDeposit.summaryInDollarByTypes,
            name = { it.termType.mName },
            color = { it.color }
        )
    }
}