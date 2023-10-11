package com.example.maliciousapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.content.ComponentName
import android.widget.TextView

class PartOne : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receive_intent)

        intent.data?.let { fileUri ->
            findViewById<TextView>(R.id.text).text = "Content: ${ fileUri.toString() }"
        }
    }
}
