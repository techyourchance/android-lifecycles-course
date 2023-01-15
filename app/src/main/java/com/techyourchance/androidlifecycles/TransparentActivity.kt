package com.techyourchance.androidlifecycles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber


class TransparentActivity : AppCompatActivity() {

    private lateinit var backgroundDetector: BackgroundDetector

    override fun onCreate(savedInstanceState: Bundle?) {

        Timber.i("onCreate()")

        val application = this.application

        super.onCreate(savedInstanceState)

        backgroundDetector = (application as CustomApplication).backgroundDetector

        setContentView(R.layout.activity_transparent)
    }

    override fun onDestroy() {
        Timber.i("onDestroy()")
        super.onDestroy()
    }

    override fun onStart() {
        Timber.i("onStart()")
        super.onStart()
        backgroundDetector.activityStarted()
    }

    override fun onStop() {
        Timber.i("onStop()")
        super.onStop()
        backgroundDetector.activityStopped()
    }

    override fun onResume() {
        Timber.i("onResume()")
        super.onResume()
    }

    override fun onPause() {
        Timber.i("onPause()")
        super.onPause()
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        Timber.i("onTopResumedActivityChanged(); isTopResumed: $isTopResumedActivity")
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, TransparentActivity::class.java)
            context.startActivity(intent)
        }
    }
}