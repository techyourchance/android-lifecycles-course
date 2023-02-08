package com.techyourchance.androidlifecycles.fragments

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.techyourchance.androidlifecycles.BackgroundDetector
import com.techyourchance.androidlifecycles.CustomApplication
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
            val intent = Intent(context, FragmentHostActivity::class.java)
            context.startActivity(intent)
        }
    }

}

private class MyFragmentLifecycleCallbacks : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentPreAttached(fm, f, context)
        Timber.d("onFragmentPreAttached(): ${getFragmentName(f)}")
    }

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentAttached(fm, f, context)
        Timber.d("onFragmentAttached(): ${getFragmentName(f)}")
    }

    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentPreCreated(fm, f, savedInstanceState)
        Timber.d("onFragmentPreCreated(): ${getFragmentName(f)}")
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentCreated(fm, f, savedInstanceState)
        Timber.d("onFragmentCreated(): ${getFragmentName(f)}")
    }

    override fun onFragmentActivityCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState)
        Timber.d("onFragmentActivityCreated(): ${getFragmentName(f)}")
    }

    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
        Timber.d("onFragmentViewCreated(): ${getFragmentName(f)}")
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        super.onFragmentStarted(fm, f)
        Timber.d("onFragmentStarted(): ${getFragmentName(f)}")
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fm, f)
        Timber.d("onFragmentResumed(): ${getFragmentName(f)}")
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        super.onFragmentPaused(fm, f)
        Timber.d("onFragmentPaused(): ${getFragmentName(f)}")
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        super.onFragmentStopped(fm, f)
        Timber.d("onFragmentStopped(): ${getFragmentName(f)}")
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        super.onFragmentSaveInstanceState(fm, f, outState)
        Timber.d("onFragmentSaveInstanceState(): ${getFragmentName(f)}")
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentViewDestroyed(fm, f)
        Timber.d("onFragmentViewDestroyed(): ${getFragmentName(f)}")
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentDestroyed(fm, f)
        Timber.d("onFragmentDestroyed(): ${getFragmentName(f)}")
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        super.onFragmentDetached(fm, f)
        Timber.d("onFragmentDetached(): ${getFragmentName(f)}")
    }

    fun getFragmentName(f: Fragment): String {
        return f.javaClass.simpleName
    }
}
