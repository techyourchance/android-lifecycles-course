package com.techyourchance.androidlifecycles.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import timber.log.Timber

class FragmentViewModel: ViewModel() {

    private var countJob: Job? = null

    val isCounting = MutableLiveData<Boolean>(false)

    val count = MutableLiveData<Int>(0)

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    fun toggleCounter() {
        if (isCounting.value!!) {
            stopCounter()
        } else {
            startCounter()
        }
    }

    private fun startCounter() {
        if (isCounting.value!!) {
            return
        }
        isCounting.value = true
        count.value = 0
        countJob = coroutineScope.launch {
            Timber.tag("FragmentViewModel").i("Starting the counter")
            while (isCounting.value!!) {
                count.value = count.value!! + 1
                Timber.tag("FragmentViewModel").i("Counter value: ${count.value!!}")
                delay(1000)
            }
        }
    }

    private fun stopCounter() {
        Timber.i("Stopping the counter")
        isCounting.value = false
        countJob?.cancel()
    }

    override fun onCleared() {
        Timber.i("onCleared()")
        super.onCleared()
        countJob?.cancel()
    }
}