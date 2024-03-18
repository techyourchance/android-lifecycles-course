package com.techyourchance.androidlifecycles.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.techyourchance.androidlifecycles.CustomApplication
import com.techyourchance.androidlifecycles.R
import timber.log.Timber

class MyForegroundService: Service() {

    private lateinit var myServiceManager: MyServiceManager
    private lateinit var notificationManager: NotificationManager

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        Timber.d("onCreate()")
        super.onCreate()
        myServiceManager = (application as CustomApplication).myServiceManager
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onDestroy() {
        Timber.d("onDestroy()")
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand()")
        if (intent?.hasExtra(EXTRA_COMMAND_START) == true) {
            Timber.d("Start service command")
            startForeground(ID, newNotification())
            myServiceManager.foregroundServiceState.value = MyServiceManager.MyServiceState.STARTED
        }
        if (intent?.hasExtra(EXTRA_COMMAND_STOP) == true) {
            Timber.d("Stop service command")
            stopSelf()
            myServiceManager.foregroundServiceState.value = MyServiceManager.MyServiceState.STOPPED
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun newNotification(): Notification {
        val intent = Intent(this, ServiceActivity::class.java)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        createServiceNotificationChannel()

        return Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("FS notification title")
            .setContentText("FS notification text")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createServiceNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "FS channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        notificationManager.createNotificationChannel(channel)
    }

    companion object {

        private const val EXTRA_COMMAND_START = "EXTRA_COMMAND_START"
        private const val EXTRA_COMMAND_STOP = "EXTRA_COMMAND_STOP"

        private const val ID = 1001
        private const val CHANNEL_ID = "fs_channel_id"

        fun start(context: Context) {
            val intent = Intent(context, MyForegroundService::class.java)
            intent.putExtra(EXTRA_COMMAND_START, true)
            context.startForegroundService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, MyForegroundService::class.java)
            intent.putExtra(EXTRA_COMMAND_STOP, true)
            context.startForegroundService(intent)
        }
    }
}