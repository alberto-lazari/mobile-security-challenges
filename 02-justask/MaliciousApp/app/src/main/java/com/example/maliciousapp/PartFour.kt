package com.example.maliciousapp

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PartFour : AppCompatActivity() {
    fun unwrapBundle(bundle: Bundle): String {
        // Get the first key (assume only one key in the bundle)
        val key = bundle
            .keySet()
            .iterator()
            .next()

        bundle.getBundle(key)?.let {
            return unwrapBundle(it)
        }

        return bundle.getString(key) ?: "[null]"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<TextView>(R.id.debug_text)
        val partialFlag = intent?.getStringExtra("flag") ?: "[null]"

        val contract = ActivityResultContracts.StartActivityForResult()
        registerForActivityResult(contract) {
            it
                .data
                ?.getExtras()
                ?.let {
                    val flagPart = unwrapBundle(it)
                    Log.w("MOBIOTSEC", "Part 4: $flagPart")

                    val flag = partialFlag + flagPart
                    view.text = flag
                    Log.w("MOBIOTSEC", "The flag is $flag")
                }
        }.launch(Intent("com.example.victimapp.intent.action.JUSTASKBUTNOTSOSIMPLE"))
    }
}
