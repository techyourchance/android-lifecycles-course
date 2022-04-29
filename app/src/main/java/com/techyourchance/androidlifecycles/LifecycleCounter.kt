package com.techyourchance.androidlifecycles

import timber.log.Timber

class LifecycleCounter {

    private var activityOnCreateCounter = 0

    fun incrementActivityOnCreateCount() {
        activityOnCreateCounter++
        Timber.i("Activity onCreate() count: $activityOnCreateCounter")
    }
}