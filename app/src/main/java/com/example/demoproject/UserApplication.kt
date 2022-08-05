package com.example.demoproject

import android.app.Application
import com.example.demoproject.di.ApplicationComponent
import com.example.demoproject.di.DaggerApplicationComponent

class UserApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}
