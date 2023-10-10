package com.example.maliciousapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.TextView
import org.apache.commons.codec.digest.DigestUtils
import android.app.Activity

class HashFile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hash_file)

        val fileUri = intent.data
        if (fileUri != null) {
            val fileInputStream = contentResolver.openInputStream(fileUri)
            val hash = DigestUtils.sha256Hex(fileInputStream)

            // Print hash on the app for debug
            findViewById<TextView>(R.id.text).text = "Hash: $hash"

            setResult(Activity.RESULT_OK, Intent().putExtra("hash", hash))
            finish()
        }
    }
}
