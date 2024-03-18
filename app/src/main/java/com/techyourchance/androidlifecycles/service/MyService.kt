package com.techyourchance.androidlifecycles.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.techyourchance.androidlifecycles.CustomApplication
import timber.log.Timber

class MyService: Service() {


    private lateinit var myServiceManager: MyServiceManager

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        Timber.d("onCreate()")
        super.onCreate()
        myServiceManager  = (application as CustomApplication).myServiceManager
    }

    override fun onDestroy() {
        Timber.d("onDestroy()")
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand()")
        if (intent?.hasExtra(EXTRA_COMMAND_START) == true) {
            Timber.d("Start service command")
            myServiceManager.serviceState.value = MyServiceManager.MyServiceState.STARTED
        }
        if (intent?.hasExtra(EXTRA_COMMAND_STOP) == true) {
            Timber.d("Stop service command")
            stopSelf()
            myServiceManager.serviceState.value = MyServiceManager.MyServiceState.STOPPED
        }

        return super.onStartCommand(intent, flags, startId)
    }

    companion object {

        private val EXTRA_COMMAND_START = "EXTRA_COMMAND_START"
        private val EXTRA_COMMAND_STOP = "EXTRA_COMMAND_STOP"

        fun start(context: Context) {
            val intent = Intent(context, MyService::class.java)
            intent.putExtra(EXTRA_COMMAND_START, true)
            context.startService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, MyService::class.java)
            intent.putExtra(EXTRA_COMMAND_STOP, true)
            context.startService(intent)
        }
    }
}