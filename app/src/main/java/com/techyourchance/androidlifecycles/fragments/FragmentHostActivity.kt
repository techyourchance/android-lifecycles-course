package com.techyourchance.androidlifecycles.fragments

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.androidlifecycles.BackgroundDetector
import com.techyourchance.androidlifecycles.CustomApplication
import com.techyourchance.androidlifecycles.MyFragmentLifecycleCallbacks
import com.techyourchance.androidlifecycles.R
import timber.log.Timber

class FragmentHostActivity : AppCompatActivity() {

    private lateinit var backgroundDetector: BackgroundDetector

    private lateinit var btnAddRemoveFragment: Button

    private var isFragmentAdded = false

    override fun onCreate(savedInstanceState: Bundle?) {

        Timber.i("onCreate()")

        val application = this.applicationContext as Application

        super.onCreate(savedInstanceState)

        backgroundDetector = (application as CustomApplication).backgroundDetector

        isFragmentAdded = savedInstanceState?.getBoolean("isFragmentAdded", false) ?: false

        setContentView(R.layout.activity_fragment_host)

        btnAddRemoveFragment = findViewById(R.id.btnAddRemoveFragment)
        btnAddRemoveFragment.setOnClickListener {
            if (isFragmentAdded) {
                Timber.i("Button clicked: remove Fragment")
                removeFragment()
            } else {
                Timber.i("Button clicked: add Fragment")
                addFragment()
            }
            isFragmentAdded = !isFragmentAdded
            updateButtonText()
        }

        updateButtonText()

        supportFragmentManager.registerFragmentLifecycleCallbacks(MyFragmentLifecycleCallbacks(), false)
    }

    private fun updateButtonText() {
        if (isFragmentAdded) {
            btnAddRemoveFragment.text = "Remove fragment"
        } else {
            btnAddRemoveFragment.text = "Add fragment"
        }
    }

    private fun addFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, Fragment1.newInstance(), "fragmentTag")
            .commit()
    }

    private fun removeFragment() {
        supportFragmentManager
            .beginTransaction()
            .remove(supportFragmentManager.findFragmentByTag("fragmentTag")!!)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isFragmentAdded", isFragmentAdded)
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

    override fun onDestroy() {
        Timber.i("onDestroy()")
        super.onDestroy()
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        Timber.i("onTopResumedActivityChanged(); isTopResumed: $isTopResumedActivity")
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, FragmentHostActivity::class.java)
            context.startActivity(intent)
        }
    }

}

