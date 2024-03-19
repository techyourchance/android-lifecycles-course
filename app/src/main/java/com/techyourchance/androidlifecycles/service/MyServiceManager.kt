package com.techyourchance.androidlifecycles.service

import kotlinx.coroutines.flow.MutableStateFlow

class MyServiceManager {

    enum class MyServiceState { NOT_STARTED, STARTED, STOPPED }

    var serviceState = MutableStateFlow(MyServiceState.NOT_STARTED)
    var foregroundServiceState = MutableStateFlow(MyServiceState.NOT_STARTED)
    var overlayServiceActive = MutableStateFlow(false)

}