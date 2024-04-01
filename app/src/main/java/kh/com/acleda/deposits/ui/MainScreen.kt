package kh.com.acleda.deposits.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import kh.com.acleda.deposits.components.AnimateWaveLineBackground
import kh.com.acleda.deposits.ui.theme.DepositsTheme

@AndroidEntryPoint
class MainScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DepositsTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AnimateWaveLineBackground()
        Text(text = "Hi mom!", color = MaterialTheme.colorScheme.error)
    }
}

@Preview
@Composable
private fun AppPreview() {
    DepositsTheme {
        App()
    }
}


