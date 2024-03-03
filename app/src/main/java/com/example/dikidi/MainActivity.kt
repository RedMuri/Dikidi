package com.example.dikidi.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.dikidi.ui.theme.DikidiTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: DefaultRootComponent.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as DikidiApp).applicationComponent.inject(this)
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


