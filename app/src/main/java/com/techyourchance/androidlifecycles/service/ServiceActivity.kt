package com.techyourchance.androidlifecycles.service

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.androidlifecycles.R

class ServiceActivity : AppCompatActivity() {


    private lateinit var btnToggleService: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        val application = this.applicationContext as Application

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_service)

        btnToggleService = findViewById(R.id.btnToggleService)

        btnToggleService.setOnClickListener {

        }
    }


    companion object {

        fun start(context: Context) {
            val intent = Intent(context, ServiceActivity::class.java)
            context.startActivity(intent)
        }
    }
}