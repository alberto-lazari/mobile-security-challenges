package com.example.maliciousapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyLocationService : Service() {
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        log("service started")
        return Service.START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    fun log(message: String) {
        Log.d("MOBIOTSEC", message)
    }
}
