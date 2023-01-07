package com.techyourchance.androidlifecycles

import android.util.Log

class BackgroundDetector {

    private var startedActivitiesNum = 0

    fun activityStarted() {
        startedActivitiesNum++
        if (startedActivitiesNum == 1) {
            Log.i(TAG, "application is in foreground")
        }
    }

    fun activityStopped() {
        startedActivitiesNum--
        if (startedActivitiesNum == 0) {
            Log.i(TAG, "application is in background")
        }
    }

    companion object {
        private const val TAG = "BackgroundDetector"
    }
}