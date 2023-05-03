package com.girogevoro.dictionary

import android.app.Application
import com.girogevoro.dictionary.di.KoinModules
import org.koin.core.context.startKoin

class DictionaryApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(KoinModules.application, KoinModules.mainScreen)
        }
    }

}
