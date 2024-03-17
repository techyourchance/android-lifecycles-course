package com.techyourchance.androidlifecycles.service

import kotlinx.coroutines.flow.MutableStateFlow

class MyServiceManager {

    enum class MyServiceState { NOT_STARTED, STARTED, STOPPED }

    var state = MutableStateFlow(MyServiceState.NOT_STARTED)

}