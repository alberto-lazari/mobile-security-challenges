package com.example.maliciousapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import org.apache.commons.codec.digest.DigestUtils
import java.io.InputStream
import java.io.FileInputStream
import java.io.IOException
import android.content.Context
import android.net.Uri
import android.app.Activity

class HashFile : AppCompatActivity() {
    @Throws(IOException::class)
    fun getFileInputStream(context: Context, contentUri: Uri): FileInputStream? {
        val inputStream: InputStream? = context.contentResolver.openInputStream(contentUri)
        return if (inputStream is FileInputStream) inputStream else null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hash_file)

        // val intent = intent

        var hash: String? = null
        try {
            val fileInputStream = getFileInputStream(this, intent.data!!)
            hash = DigestUtils.sha256Hex(fileInputStream)
        } catch (e: Exception) {
            hash = e.message
        }

        // Print hash on the app for debug
        findViewById<TextView>(R.id.text).text = "Hash: $hash"

        setResult(Activity.RESULT_OK, Intent().putExtra("hash", hash))
        finish()
    }
}
