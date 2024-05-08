package kh.com.acleda.deposits.modules.openNewTerm.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.components.CenterTopAppBar
import kh.com.acleda.deposits.components.button.SUButton
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold6
import kh.com.acleda.deposits.ui.theme.Gray1
import kh.com.acleda.deposits.ui.theme.Green3
import kh.com.acleda.deposits.ui.theme.White

@Composable
fun OpenNewTermSuccessScreen(
    modifier: Modifier = Modifier,
    onClickViewDepositList: () -> Unit = {}
) {
    CenterTopAppBar(
        title = "Open New Term",
        hasBackButton = false
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = White.copy(alpha = 0.2f),
                    modifier = Modifier
                        .height(274.dp)
                        .padding(horizontal = 32.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.padding(horizontal = 40.dp, vertical = 24.dp)
                    ) {
                        Image(
                            painterResource(R.drawable.img_success),
                            contentDescription = null
                        )

                        Text(
                            text = "Completed",
                            style = MaterialTheme.typography.titleLarge,
                            color = DepositsTheme.colors.textPrimary
                        )

                        Text(
                            text = "Please check your new term in the list",
                            style = MaterialTheme.typography.labelLarge,
                            color = Green3,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            SUButton(
                text = "view deposit list",
                shape = RectangleShape,
                textColor = Gray1,
                bodyColor = Gold6,
                onClick = onClickViewDepositList
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        OpenNewTermSuccessScreen()
    }
}