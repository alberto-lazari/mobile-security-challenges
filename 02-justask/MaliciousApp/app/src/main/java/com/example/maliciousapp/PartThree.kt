package com.example.maliciousapp

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PartThree : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val partialFlag = intent?.getStringExtra("flag") ?: "[null]"
        val intent = Intent().apply {
            component = ComponentName(
                "com.example.victimapp",
                "com.example.victimapp.PartThree"
            )
        }

        val contract = ActivityResultContracts.StartActivityForResult()
        registerForActivityResult(contract) { result ->
            val flagPart = result
                .data
                ?.getStringExtra("hiddenFlag")
                ?: "[null]"

            Log.w("MOBIOTSEC", "Part 3: $flagPart")
            startActivity(
                Intent(this, PartFour::class.java)
                .putExtra("flag", partialFlag + flagPart)
            )
        }.launch(intent)
    }
}
