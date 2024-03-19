package com.techyourchance.androidlifecycles.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.techyourchance.androidlifecycles.CustomApplication
import com.techyourchance.androidlifecycles.R
import timber.log.Timber
import kotlin.math.min

class MyOverlayService: Service() {

    private lateinit var myServiceManager: MyServiceManager
    private lateinit var windowManager: WindowManager

    private var overlayView: View? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        Timber.d("onCreate()")
        super.onCreate()
        myServiceManager = (application as CustomApplication).myServiceManager
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    override fun onDestroy() {
        Timber.d("onDestroy()")
        super.onDestroy()
        hideOverlay()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand()")
        if (intent.hasExtra(EXTRA_COMMAND_START)) {
            Timber.d("Start service command")
            showOverlay()
        }
        if (intent.hasExtra(EXTRA_COMMAND_STOP)) {
            Timber.d("Stop service command")
            stopSelf()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun showOverlay() {
        myServiceManager.overlayServiceActive.value = true

        if (overlayView != null) {
            Timber.i("overlay already shown - aborting")
            return
        }

        overlayView = LayoutInflater.from(this).inflate(R.layout.element_overlay, null)

        val params = getOverlayLayoutParams()

        windowManager.addView(overlayView, params)

    }

    private fun getOverlayLayoutParams(): ViewGroup.LayoutParams {
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels

        val size = (min(screenWidth, screenHeight) * 0.25).toInt() // 25% of the smallest dimension

        val params = WindowManager.LayoutParams(
            size,
            size,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.TOP or Gravity.START
        params.x = (screenWidth - size) / 2 // center horizontally
        params.y = screenHeight / 10 // at the top of the screen

        return params
    }

    private fun hideOverlay() {
        myServiceManager.overlayServiceActive.value = false
        if (overlayView == null) {
            Timber.i("overlay not shown - aborting")
            return
        }
        windowManager.removeView(overlayView)
        overlayView = null
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        hideOverlay()
        showOverlay()
    }

    companion object {

        private const val EXTRA_COMMAND_START = "EXTRA_COMMAND_START"
        private const val EXTRA_COMMAND_STOP = "EXTRA_COMMAND_STOP"

        fun showOverlay(context: Context) {
            val intent = Intent(context, MyOverlayService::class.java)
            intent.putExtra(EXTRA_COMMAND_START, true)
            context.startService(intent)
        }

        fun hideOverlay(context: Context) {
            val intent = Intent(context, MyOverlayService::class.java)
            intent.putExtra(EXTRA_COMMAND_STOP, true)
            context.startService(intent)
        }
    }
}