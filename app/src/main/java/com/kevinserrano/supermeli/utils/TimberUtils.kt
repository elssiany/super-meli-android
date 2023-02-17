package com.kevinserrano.supermeli.utils

import android.util.Log
import com.kevinserrano.supermeli.BuildConfig
import timber.log.Timber

fun initTimber() {
    if (BuildConfig.DEBUG) {
        Timber.plant(Timber.DebugTree())
    }
}

fun timberLog(message: String, tag: String = "Timber", priority: Int = Log.INFO) {
    Timber.tag(tag).log(priority, message)
}