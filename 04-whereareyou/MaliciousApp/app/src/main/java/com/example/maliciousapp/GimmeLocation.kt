package com.example.maliciousapp

import android.app.Service 
import android.content.Intent
import android.os.IBinder

class GimmeLocation : Service() {
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        // We don't provide binding, so return null
        return null
    }
}
