package com.example.dikidi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.dikidi.data.network.ApiFactory
import com.example.dikidi.ui.screens.MainScreen
import com.example.dikidi.ui.theme.DikidiTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableRenderUnderSystemBar()
        setContent {
            DikidiTheme {
                MainScreen()
            }
        }
    }

    private fun enableRenderUnderSystemBar() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}


