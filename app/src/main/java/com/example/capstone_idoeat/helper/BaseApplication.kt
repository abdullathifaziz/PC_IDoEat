package com.example.capstone_idoeat.helper

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class BaseApplication : Application() {

    companion object {
        const val NOTIFICATION = "notifikasi"
        const val CHANNEL_ID = 101
    }

    override fun onCreate() {
        super.onCreate()
        createNotification()
    }

    private fun createNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notifikasi = NotificationChannel(
                NOTIFICATION,
                "Notifikasi",
                NotificationManager.IMPORTANCE_HIGH
            )
            notifikasi.description = "Data Kalori Ideal Yang Baru Telah Ditambahkan"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(notifikasi)
        }
    }

}