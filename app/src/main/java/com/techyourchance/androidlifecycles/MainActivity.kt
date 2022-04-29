package com.techyourchance.androidlifecycles

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnNextActivity).setOnClickListener {
            SecondActivity.start(this)
        }

        Timber.i(applicationContext.toString())
    }
}