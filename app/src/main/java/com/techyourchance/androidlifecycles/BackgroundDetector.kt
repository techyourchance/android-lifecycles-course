package com.techyourchance.androidlifecycles

import android.os.Handler
import android.os.Looper
import com.techyourchance.androidlifecycles.sharedpreferences.SharedPrefsHelper
import timber.log.Timber

class BackgroundDetector(private val sharedPrefsHelper: SharedPrefsHelper) {

    interface Listener {
        fun onBackground()
        fun onForeground()
    }


    private val uiHandler = Handler(Looper.getMainLooper())

    private val onStopRunnable: () -> Unit = {
        startedActivitiesNum--
        if (startedActivitiesNum == 0) {
            Timber.tag("BackgroundDetector").i("application is in background")
            listeners.map { it.onBackground() }
        }
    }

    private var startedActivitiesNum = 0

    private val listeners = mutableListOf<Listener>()

    fun activityStarted() {
        startedActivitiesNum++
        if (startedActivitiesNum == 1) {
            var foregroundCount = sharedPrefsHelper.foregroundCount().get()
            foregroundCount++
            sharedPrefsHelper.foregroundCount().set(foregroundCount)
            Timber.i("application is in foreground; foreground count: $foregroundCount")
            listeners.map { it.onForeground() }
        }
    }

    fun activityStopped() {
        uiHandler.postDelayed(onStopRunnable, STOP_DELAY_MS)
    }

    fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: Listener) {
        listeners.remove(listener)
    }

    companion object {
        const val STOP_DELAY_MS = 500L
    }
}