package com.techyourchance.androidlifecycles.service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.androidlifecycles.CustomApplication
import com.techyourchance.androidlifecycles.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ServiceActivity : AppCompatActivity() {

    private lateinit var btnToggleService: Button
    private lateinit var btnToggleForegroundService: Button

    private lateinit var myServiceManager: MyServiceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val application = this.applicationContext as CustomApplication
        myServiceManager = application.myServiceManager

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_service)

        btnToggleService = findViewById(R.id.btnToggleService)
        btnToggleForegroundService = findViewById(R.id.btnToggleForegroundService)

        btnToggleService.setOnClickListener {
            when(myServiceManager.serviceState.value) {
                MyServiceManager.MyServiceState.NOT_STARTED, MyServiceManager.MyServiceState.STOPPED -> {
                    MyService.start(this@ServiceActivity)
                }
                MyServiceManager.MyServiceState.STARTED -> {
                    MyService.stop(this@ServiceActivity)
                }
            }
        }

        btnToggleForegroundService.setOnClickListener {
            when(myServiceManager.foregroundServiceState.value) {
                MyServiceManager.MyServiceState.NOT_STARTED, MyServiceManager.MyServiceState.STOPPED -> {
                    MyForegroundService.start(this@ServiceActivity)
                }
                MyServiceManager.MyServiceState.STARTED -> {
                    MyForegroundService.stop(this@ServiceActivity)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch(Dispatchers.Main) {
            combine(myServiceManager.serviceState, myServiceManager.foregroundServiceState) { ss, fss ->
                Pair(ss, fss)
            }.collect { pair ->
                updateUi(pair.first, pair.second)
            }
        }
    }

    private fun updateUi(serviceState: MyServiceManager.MyServiceState, foregroundServiceState: MyServiceManager.MyServiceState) {
        when (serviceState) {
            MyServiceManager.MyServiceState.NOT_STARTED -> {
                btnToggleService.text = "Start service"
            }
            MyServiceManager.MyServiceState.STARTED -> {
                btnToggleService.text = "Stop service"
            }
            MyServiceManager.MyServiceState.STOPPED -> {
                btnToggleService.text = "Restart service"
            }
        }
        when (foregroundServiceState) {
            MyServiceManager.MyServiceState.NOT_STARTED -> {
                btnToggleForegroundService.text = "Start foreground service"
            }
            MyServiceManager.MyServiceState.STARTED -> {
                btnToggleForegroundService.text = "Stop foreground service"
            }
            MyServiceManager.MyServiceState.STOPPED -> {
                btnToggleForegroundService.text = "Restart foreground service"
            }
        }
    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, ServiceActivity::class.java)
            context.startActivity(intent)
        }
    }
}