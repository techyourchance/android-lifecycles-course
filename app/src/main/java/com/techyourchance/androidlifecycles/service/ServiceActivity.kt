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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ServiceActivity : AppCompatActivity() {

    private lateinit var btnToggleService: Button

    private lateinit var myServiceManager: MyServiceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val application = this.applicationContext as CustomApplication
        myServiceManager = application.myServiceManager

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_service)

        btnToggleService = findViewById(R.id.btnToggleService)

        btnToggleService.setOnClickListener {
            when(myServiceManager.state.value) {
                MyServiceManager.MyServiceState.NOT_STARTED, MyServiceManager.MyServiceState.STOPPED -> {
                    MyService.start(this@ServiceActivity)
                }
                MyServiceManager.MyServiceState.STARTED -> {
                    MyService.stop(this@ServiceActivity)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch(Dispatchers.Main) {
            myServiceManager.state.collect {state ->
                updateUi(state)
            }
        }
    }

    private fun updateUi(state: MyServiceManager.MyServiceState) {
        when (state) {
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
    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, ServiceActivity::class.java)
            context.startActivity(intent)
        }
    }
}