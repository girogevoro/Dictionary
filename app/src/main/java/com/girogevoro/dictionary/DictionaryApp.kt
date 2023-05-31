package com.girogevoro.dictionary

import android.app.Application
import com.girogevoro.dictionary.di.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DictionaryApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(KoinModules.application, KoinModules.mainScreen, KoinModules.historyScreen)
        }
    }

}
