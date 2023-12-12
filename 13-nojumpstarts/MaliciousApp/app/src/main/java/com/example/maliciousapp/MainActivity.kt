package com.example.maliciousapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    val TAG = "MOBIOTSEC"
    val contract = ActivityResultContracts.StartActivityForResult()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Main.buildIntent("B", "C", "Main-to-A/A-to-B")
        registerForActivityResult(contract) {
            val flag = it.data
                ?.getStringExtra("flag")
                ?: "[null]"
            Log.d(TAG, "The flag is $flag")
            findViewById<TextView>(R.id.debug_text).text = flag
        }.launch(intent)
    }
}
