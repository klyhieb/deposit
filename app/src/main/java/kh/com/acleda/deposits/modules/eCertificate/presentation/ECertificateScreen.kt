package kh.com.acleda.deposits.modules.eCertificate.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.components.BadgeWithText
import kh.com.acleda.deposits.components.CenterTopAppBar
import kh.com.acleda.deposits.components.button.BaseButton
import kh.com.acleda.deposits.modules.depositList.presentation.component.getCcyEnum
import kh.com.acleda.deposits.modules.depositList.presentation.component.getTermTypeEnum
import kh.com.acleda.deposits.modules.eCertificate.domain.model.ECertificateModel
import kh.com.acleda.deposits.modules.home.domain.model.TermType
import kh.com.acleda.deposits.modules.home.presentation.components.BlurryImage
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import kh.com.acleda.deposits.modules.home.presentation.components.TextBalance
import kh.com.acleda.deposits.ui.theme.Badge
import kh.com.acleda.deposits.ui.theme.Blue1
import kh.com.acleda.deposits.ui.theme.Blue2
import kh.com.acleda.deposits.ui.theme.Blue8
import kh.com.acleda.deposits.ui.theme.DepositsTheme
import kh.com.acleda.deposits.ui.theme.Gold6
import kh.com.acleda.deposits.ui.theme.Gray1
import kh.com.acleda.deposits.ui.theme.White

@Composable
fun ECertificateScreen(
    modifier: Modifier = Modifier,
    model: ECertificateModel,
    onBackPress: () -> Unit = {}
) {
    CenterTopAppBar(
        title = "Deposit Detail",
        onBackClick = onBackPress
    ) { innerPadding ->
        val state = rememberLazyListState()
        val containerBackground = Brush.radialGradient(
            colors = listOf(Blue2.copy(alpha = 0.5f), Blue1.copy(alpha = 0.5f)),
        )

        val currency = getCcyEnum(model.ccy)
        val depositType = getTermTypeEnum(model.termTypeId)


        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(containerBackground),
            contentAlignment = Alignment.TopCenter
        ) {

            LazyColumn (
                contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp),
                state = state
            ) {

                item {
                    /* header */
                    ECertificateHeader(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 26.dp),
                        depositType = depositType,
                        mmNumber = model.mmNumber,
                        depositAmount = model.depositAmount,
                        ccy = currency
                    )
                }
                item {
                    /* pdf or image of E-Certificate */
                    BlurryImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        image = R.drawable.ecertificate,
                        blurRadio = 10.dp
                    )
                }
            }

            ShareSaveButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                onShareClick = {},
                onSaveClick = {}
            )
        }
    }
}

@Composable
fun ECertificateHeader(
    modifier: Modifier = Modifier,
    depositType: TermType,
    mmNumber: String,
    depositAmount: String,
    ccy: CCY,
) {
    val ccyTextStyle = MaterialTheme.typography.headlineMedium.copy(
        fontWeight = FontWeight.Medium
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Surface(
            shape = CircleShape,
            color = Badge,
            modifier = Modifier.size(54.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_e_certificate),
                contentDescription = null,
                tint = Color(0xFF05A3D3),
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = mmNumber,
            style = MaterialTheme.typography.headlineMedium,
            color = DepositsTheme.colors.textPrimary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Deposit amount:",
            style = MaterialTheme.typography.titleSmall,
            color = DepositsTheme.colors.textSecondary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextBalance(
                balance = depositAmount,
                ccy = ccy,
                textStyle = ccyTextStyle
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = ccy.dec.uppercase(),
                style = ccyTextStyle,
                color = White
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        BadgeWithText(
            text = depositType.mName,
            containerColor = Blue2.copy(alpha = 0.6f),
            textStyle = MaterialTheme.typography.titleMedium,
            shape = RoundedCornerShape(24.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun ShareSaveButton(
    modifier: Modifier = Modifier,
    onShareClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .background(Blue1.copy(alpha = 0.4f))
            .padding(bottom = 16.dp, top = 8.dp)
            .padding(horizontal = 16.dp)
    ) {
        BaseButton(
            shape = RoundedCornerShape(8.dp),
            text = "share",
            textColor = Blue8,
            bodyColor = Gray1,
            onClick = onShareClick,
            modifier = Modifier.weight(0.5f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        BaseButton(
            shape = RoundedCornerShape(8.dp),
            text = "save",
            textColor = Gray1,
            bodyColor = Gold6,
            onClick = onSaveClick,
            modifier = Modifier.weight(0.5f)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    DepositsTheme {
        val model = ECertificateModel(
            termTypeId = "21010",
            mmNumber = "MM2331400003",
            depositAmount = "400000000",
            ccy = "KHR"
        )
        ECertificateScreen(model = model)
    }

}

