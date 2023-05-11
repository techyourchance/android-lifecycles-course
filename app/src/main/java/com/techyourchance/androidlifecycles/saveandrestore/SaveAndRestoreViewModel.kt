package com.techyourchance.androidlifecycles.saveandrestore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import timber.log.Timber

class SaveAndRestoreViewModel: ViewModel() {

    val secondsElapsed = MutableLiveData<Int>(0)

    init {
        startCounter()
    }

    private fun startCounter() {
        viewModelScope.launch {
            while (isActive) {
                Timber.tag("FragmentViewModel").i("Starting the counter")
                secondsElapsed.value = secondsElapsed.value!! + 1
                Timber.tag("FragmentViewModel").i("Counter value: ${secondsElapsed.value!!}")
                delay(1000)
            }
        }
    }

    override fun onCleared() {
        Timber.i("onCleared()")
        super.onCleared()
    }
}