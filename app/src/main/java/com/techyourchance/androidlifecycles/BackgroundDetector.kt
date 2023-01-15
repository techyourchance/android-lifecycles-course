package com.techyourchance.androidlifecycles

import android.util.Log
import timber.log.Timber

class BackgroundDetector {

    interface Listener {
        fun onBackground()
        fun onForeground()
    }

    private var startedActivitiesNum = 0

    private val listeners = mutableListOf<Listener>()

    fun activityStarted() {
        startedActivitiesNum++
        if (startedActivitiesNum == 1) {
            Timber.i("application is in foreground")
            listeners.map { it.onForeground() }
        }
    }

    fun activityStopped() {
        startedActivitiesNum--
        if (startedActivitiesNum == 0) {
            Timber.i("application is in background")
            listeners.map { it.onBackground() }
        }
    }

    fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: Listener) {
        listeners.remove(listener)
    }
}