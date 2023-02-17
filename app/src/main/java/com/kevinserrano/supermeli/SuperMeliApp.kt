package com.kevinserrano.supermeli

import android.app.Application
import com.kevinserrano.supermeli.utils.initTimber
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SuperMeliApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }
}
