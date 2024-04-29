package com.dam.proteccioncivil

import android.app.Application
import com.dam.proteccioncivil.data.AppContainer
import com.dam.proteccioncivil.data.DefaultAppContainer

class MainApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}