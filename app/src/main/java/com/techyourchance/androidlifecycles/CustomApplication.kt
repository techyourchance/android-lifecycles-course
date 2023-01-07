package com.techyourchance.androidlifecycles

import android.app.Application

class CustomApplication : Application() {

    val backgroundDetector = BackgroundDetector()
}