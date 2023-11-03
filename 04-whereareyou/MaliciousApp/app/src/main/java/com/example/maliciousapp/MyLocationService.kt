package com.example.maliciousapp

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import android.util.Log

import androidx.core.content.ContextCompat

const val TAG = "MOBIOTSEC"
const val ACTION = "com.mobiotsec.intent.action.LOCATION_ANNOUNCEMENT"

class MyLocationService : Service() {
    override fun onCreate () {
        val listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                sendBroadcast(Intent().apply {
                    setAction(ACTION)
                    putExtra("location", location)
                })
            }
        }
        val fineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        when(fineLocationPermission) {
            PackageManager.PERMISSION_GRANTED ->
                getSystemService(LocationManager::class.java).requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1L,
                    .1f,
                    listener
                )
            else -> Log.e(TAG, "Location permission not granted")
        }

        getSystemService(NotificationManager::class.java)
            .createNotificationChannel(NotificationChannel(
                "location_service_notification",
                "Location service notification",
                NotificationManager.IMPORTANCE_DEFAULT
            ))

        startForeground(1, Notification
            .Builder(this, "location_service_notification")
            .setContentTitle("Location service")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
        )
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
