package com.techyourchance.androidlifecycles.saveandrestore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import timber.log.Timber

class SaveAndRestoreViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    val secondsElapsed = MutableLiveData<Int>(0)

    init {
        if (savedStateHandle.contains(SAVED_STATE_COUNTER)) {
            secondsElapsed.value = savedStateHandle[SAVED_STATE_COUNTER]
        }
        startCounter()
    }

    private fun startCounter() {
        viewModelScope.launch {
            while (isActive) {
                Timber.tag("FragmentViewModel").i("Starting the counter")
                secondsElapsed.value = secondsElapsed.value!! + 1
                savedStateHandle[SAVED_STATE_COUNTER] = secondsElapsed.value
                Timber.tag("FragmentViewModel").i("Counter value: ${secondsElapsed.value!!}")
                delay(1000)
            }
        }
    }

    override fun onCleared() {
        Timber.i("onCleared()")
        super.onCleared()
    }

    companion object {
        const val SAVED_STATE_COUNTER = "SAVED_STATE_COUNTER"
    }
}