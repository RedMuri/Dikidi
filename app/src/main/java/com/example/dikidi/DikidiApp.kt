package com.example.dikidi

import android.app.Application
import com.example.dikidi.di.DaggerApplicationComponent

class DikidiApp : Application() {

    val component by lazy {
        DaggerApplicationComponent
            .factory()
            .create(this)
    }
}