package com.example.maliciousapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.widget.TextView

class PartOne : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intent.data?.let { fileUri ->
            findViewById<TextView>(R.id.debug_text).text = "Content: ${ fileUri.toString() }"
        }
    }
}
