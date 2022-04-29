package com.techyourchance.androidlifecycles

import android.app.Application
import timber.log.Timber

class CustomApplication: Application() {


    val lifecycleCounter = LifecycleCounter()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}