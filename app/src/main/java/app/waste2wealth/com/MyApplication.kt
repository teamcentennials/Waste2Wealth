package app.waste2wealth.com

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            "location",
            "BackGround Location",
            NotificationManager.IMPORTANCE_LOW
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}