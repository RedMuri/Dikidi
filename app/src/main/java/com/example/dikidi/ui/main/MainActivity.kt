package com.example.dikidi.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.dikidi.ui.theme.DikidiTheme
import com.example.dikidi.ui.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    private val component by lazy {
        (application as DikidiApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
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


