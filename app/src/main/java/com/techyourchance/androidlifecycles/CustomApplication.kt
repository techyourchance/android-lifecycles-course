package com.techyourchance.androidlifecycles

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import timber.log.Timber

class CustomApplication : Application() {

    val backgroundDetector = BackgroundDetector()

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        Timber.i("onCreate()")

        ProcessLifecycleOwner.get().lifecycle.addObserver(MyAppLifecycleObserver())
    }
}

class MyAppLifecycleObserver: DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Timber.i("application is in foreground")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Timber.i("application is in background")
    }
}