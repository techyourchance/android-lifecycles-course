package com.techyourchance.androidlifecycles.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class ActivityViewModel: ViewModel() {

    private var activityRecreateCount = 0

    fun activityCreated() {
        activityRecreateCount++
    }

    fun getActivityRecreateCount(): Int {
        return activityRecreateCount
    }
}