package kh.com.acleda.deposits.ui

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import kh.com.acleda.deposits.modules.openNewTerm.presentation.OpenNewTermScreen
import kh.com.acleda.deposits.ui.theme.DepositsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            )
        )
        setContent {
            DepositsTheme {
                DepositsApp()
            }
        }
    }
}