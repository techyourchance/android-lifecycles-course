package com.techyourchance.androidlifecycles

import android.util.Log
import timber.log.Timber

class BackgroundDetector {

    private var startedActivitiesNum = 0

    fun activityStarted() {
        startedActivitiesNum++
        if (startedActivitiesNum == 1) {
            Timber.i("application is in foreground")
        }
    }

    fun activityStopped() {
        startedActivitiesNum--
        if (startedActivitiesNum == 0) {
            Timber.i("application is in background")
        }
    }

}