package com.example.maliciousapp

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.Looper
import android.util.Log

import androidx.core.content.ContextCompat

import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class MyLocationService : Service() {
    private val TAG = "MOBIOTSEC"

    override fun onCreate () {
        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            val locationRequest = LocationRequest
                .Builder(1000)
                .build()
            val locationCallback: LocationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    for (location in locationResult.locations) {
                        val intent = Intent().apply {
                            setAction("com.mobiotsec.intent.action.LOCATION_ANNOUNCEMENT")
                            putExtra("location", location)
                        }
                        sendBroadcast(intent)
                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } else {
            Log.e(TAG, "Location permission not granted")
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val channel = NotificationChannel(
            "location_service_notification",
            "Location service notification",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        getSystemService(NotificationManager::class.java)
            .apply { createNotificationChannel(channel) }
        val notification = Notification.Builder(this, "location_service_notification")
            .setContentTitle("Location service")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        startForeground(1, notification)
        return Service.START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
