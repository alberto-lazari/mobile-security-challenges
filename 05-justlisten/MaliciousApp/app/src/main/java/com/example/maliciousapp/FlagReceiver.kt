package com.example.maliciousapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class FlagReceiver : BroadcastReceiver() {
    private val TAG = "MOBIOTSEC"

    override fun onReceive(context: Context, intent: Intent) {
        val flag = intent.getStringExtra("flag")
        flag?.let {
            Log.d(TAG, it)
        }
    }
}
