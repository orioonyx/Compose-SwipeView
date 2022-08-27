package com.kyungeun.compose_swipeview.ui

import android.app.Application
import com.kyungeun.compose_swipeview.BuildConfig
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}