package kh.com.acleda.deposits.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.ui.theme.DepositsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    containerColor: Color = DepositsTheme.colors.uiBackground,
    hasBackButton: Boolean = true,
    action: @Composable (RowScope.() -> Unit) = {},
    onBackClick: () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        containerColor = containerColor,
        bottomBar = bottomBar,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DepositsTheme.colors.statusBar,
                    titleContentColor = DepositsTheme.colors.textPrimary,
                ),
                title = {
                    Text(title, style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ))
                },
                navigationIcon = {
                    if (hasBackButton) {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back_arrow),
                                contentDescription = null,
                                tint = DepositsTheme.colors.textPrimary
                            )
                        }
                    }
                },
                actions = action
            )
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}