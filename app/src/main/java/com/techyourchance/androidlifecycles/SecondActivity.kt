package com.techyourchance.androidlifecycles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber


class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate()")
        super.onCreate(savedInstanceState)

        (applicationContext as CustomApplication).lifecycleCounter.incrementActivityOnCreateCount()

        setContentView(R.layout.activity_second)

        Timber.i(applicationContext.toString())
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, SecondActivity::class.java)
            context.startActivity(intent)
        }
    }
}