package com.kevinserrano.supermeli

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SuperMeliApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
