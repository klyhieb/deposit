package kh.com.acleda.deposits.ui

import android.graphics.Color
import android.os.Bundle
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kh.com.acleda.deposits.modules.depositList.presentation.DepositDetailScreen
import kh.com.acleda.deposits.modules.depositList.presentation.DepositListScreen
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

        /* Required Status bar back */
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)

        setContent { DepositsApp() }
    }
}