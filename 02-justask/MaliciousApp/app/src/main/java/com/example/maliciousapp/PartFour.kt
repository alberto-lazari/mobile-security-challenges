package com.example.maliciousapp

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PartFour : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<TextView>(R.id.debug_text)
        val partialFlag = intent?.getStringExtra("flag") ?: "[null]"

        val contract = ActivityResultContracts.StartActivityForResult()
        registerForActivityResult(contract) { result ->
            var bundle = result
                .data
                ?.getBundleExtra("follow")
            val keys = listOf(
                "the value",
                "rabbit",
                "hole",
                "deeper",
            )
            for (key in keys) {
                bundle = bundle?.getBundle(key)
            }
            val flagPart = bundle?.getCharSequence("never ending story") ?: "[null]"
            Log.w("MOBIOTSEC", "Part 4: $flagPart")

            val flag = partialFlag + flagPart
            view.text = flag
            Log.w("MOBIOTSEC", "The flag is $flag")
        }.launch(Intent("com.example.victimapp.intent.action.JUSTASKBUTNOTSOSIMPLE"))
    }
}
