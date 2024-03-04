package com.example.dikidi.di

import android.app.Application
import com.example.data.ApplicationScope
import com.example.data.DataModule
import com.example.dikidi.MainActivity
import com.example.feature_root.RootModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [RootModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent
    }
}