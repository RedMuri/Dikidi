package com.example.dikidi

import android.app.Application
import com.example.dikidi.di.ApplicationComponent
import com.example.dikidi.di.DaggerApplicationComponent

class DikidiApp : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}