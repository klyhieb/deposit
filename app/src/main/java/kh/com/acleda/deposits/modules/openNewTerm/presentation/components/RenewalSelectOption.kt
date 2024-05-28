package kh.com.acleda.deposits.modules.openNewTerm.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold10
import kh.com.acleda.deposits.ui.theme.Gold8
import kh.com.acleda.deposits.ui.theme.Gray0
import kh.com.acleda.deposits.ui.theme.Gray1
import kh.com.acleda.deposits.ui.theme.Gray2
import kh.com.acleda.deposits.ui.theme.Gray3
import kh.com.acleda.deposits.ui.theme.Gray4
import kh.com.acleda.deposits.ui.theme.Gray5
import kh.com.acleda.deposits.ui.theme.Gray6
import kh.com.acleda.deposits.ui.theme.Gray9

@Composable
fun SingleSelectionList(
    modifier: Modifier = Modifier,
    options: List<SelectionOption>,
    onOptionClicked: (SelectionOption) -> Unit
) {
    LazyColumn (modifier) {
        items(options) { option -> SingleSelectionCard(option, onOptionClicked) }
    }
}

@Composable
fun SingleSelectionCard(selectionOption: SelectionOption, onOptionClicked: (SelectionOption) -> Unit) {

    val borderColor = if (selectionOption.selected) Gold8 else Gray3

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(vertical = 4.dp),
        color = Color.Transparent
    ) {
        Surface(
            modifier = Modifier
                .border(1.dp, borderColor, RoundedCornerShape(8.dp))
                .clickable(true, onClick = { onOptionClicked(selectionOption) }),
            shape = RoundedCornerShape(8.dp),
            color = Color.Transparent
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = selectionOption.selected,
                    onClick = {
                        onOptionClicked(selectionOption)
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Gold8,
                        unselectedColor = Gray6
                    )
                )

                Column {
                    Text(
                        text = selectionOption.model.title,
                        style = MaterialTheme.typography.titleSmall,
                        color = DepositsTheme.colors.textHelpLabel
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = selectionOption.model.des,
                        style = MaterialTheme.typography.labelMedium,
                        color = DepositsTheme.colors.textSupport
                    )
                }

            }
        }
    }
}


class SelectionOption(val model: RenewalItemModel, var initialSelectedValue: Boolean) {
    var selected by mutableStateOf(initialSelectedValue)
}

data class RenewalItemModel(
    val id: String,
    val title: String,
    val des: String,
    val isRenewal: Boolean = true
)


@Preview
@Composable
private fun Preview() {
    val selectionOption = SelectionOption(
        model = RenewalItemModel(
            id = "RO1",
            title = "No Renewal",
            des = "Principal will be credit to your account on maturity date"
        ),
        initialSelectedValue = false
    )

    DepositsTheme {
        SingleSelectionCard(
            selectionOption = selectionOption,
            onOptionClicked = {}
        )
    }
}