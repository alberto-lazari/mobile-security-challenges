package com.example.maliciousapp

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PartTwo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val partialFlag = intent?.getStringExtra("flag") ?: "[null]"

        val contract = ActivityResultContracts.StartActivityForResult()
        registerForActivityResult(contract) { result ->
            val flagPart = result
                .data
                ?.getStringExtra("flag")
                ?: "[null]"

            Log.w("MOBIOTSEC", "Part 2: $flagPart")
            startActivity(
                Intent(this, PartThree::class.java)
                    .putExtra("flag", partialFlag + flagPart)
            )
        }.launch(Intent("com.example.victimapp.intent.action.JUSTASK"))
    }
}
