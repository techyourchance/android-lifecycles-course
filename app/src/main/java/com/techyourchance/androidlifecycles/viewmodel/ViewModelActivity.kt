package com.techyourchance.androidlifecycles.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.techyourchance.androidlifecycles.BackgroundDetector
import com.techyourchance.androidlifecycles.CustomApplication
import com.techyourchance.androidlifecycles.MyFragmentLifecycleCallbacks
import com.techyourchance.androidlifecycles.R
import timber.log.Timber

class ViewModelActivity : AppCompatActivity() {

    private lateinit var backgroundDetector: BackgroundDetector

    private lateinit var viewModel: ActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        Timber.i("onCreate()")

        val application = this.applicationContext as Application

        super.onCreate(savedInstanceState)

        backgroundDetector = (application as CustomApplication).backgroundDetector

        viewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)
        //viewModel = ActivityViewModel() // BUG!

        setContentView(R.layout.activity_view_model)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, ViewModelFragment.newInstance(), "fragmentTag")
                .commit()
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(MyFragmentLifecycleCallbacks(), false)

        viewModel.activityCreated()

        findViewById<TextView>(R.id.txtRecreateCount).text = "Recreated: ${viewModel.getActivityRecreateCount()}"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Timber.i("onSaveInstanceState()")
        super.onSaveInstanceState(outState)
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
            val intent = Intent(context, ViewModelActivity::class.java)
            context.startActivity(intent)
        }
    }

}