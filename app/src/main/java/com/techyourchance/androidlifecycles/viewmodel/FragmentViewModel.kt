package com.techyourchance.androidlifecycles.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class FragmentViewModel: ViewModel() {

    var isCounting = false
        private set

    fun toggleCounter() {
        if (isCounting) {
            stopCounter()
        } else {
            startCounter()
        }
    }

    private fun startCounter() {
        viewModelScope.launch {
            if (isCounting) {
                return@launch
            }
            Timber.tag("FragmentViewModel").i("Starting the counter")
            isCounting = true
            var count = 0
            while (isCounting) {
                count++
                Timber.tag("FragmentViewModel").i("Counter value: $count")
                delay(1000)
            }
        }
    }

    private fun stopCounter() {
        Timber.i("Stopping the counter")
        isCounting = false
    }
}