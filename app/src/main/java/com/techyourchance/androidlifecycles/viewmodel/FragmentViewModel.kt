package com.techyourchance.androidlifecycles.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import timber.log.Timber

class FragmentViewModel: ViewModel() {

    interface Listener {
        fun onCounterValueChanged(count: Int)
        fun onCounterStateChanged(isCounting: Boolean)
    }

    var isCounting = false
        private set

    var count = 0
        private set

    private val listeners = mutableSetOf<Listener>()

    private var countJob: Job? = null

    fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: Listener) {
        listeners.remove(listener)
    }

    fun toggleCounter() {
        if (isCounting) {
            stopCounter()
        } else {
            startCounter()
        }
    }

    private fun startCounter() {
        if (isCounting) {
            return
        }
        isCounting = true
        count = 0
        countJob = viewModelScope.launch {
            Timber.tag("FragmentViewModel").i("Starting the counter")
            listeners.map { it.onCounterStateChanged(true) }
            while (isCounting) {
                count++
                Timber.tag("FragmentViewModel").i("Counter value: $count")
                listeners.map { it.onCounterValueChanged(count) }
                delay(1000)
            }
        }
    }

    private fun stopCounter() {
        Timber.i("Stopping the counter")
        isCounting = false
        countJob?.cancel()
        listeners.map { it.onCounterStateChanged(false) }
    }
}