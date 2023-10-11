package com.example.maliciousapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.security.MessageDigest
import org.bouncycastle.util.encoders.Hex

class HashFile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Display activity layout
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hash_file)

        // Get intent
        intent.data?.let { fileUri ->
            // Get file content bytes
            val bytes = contentResolver
                .openInputStream(fileUri)
                ?.readAllBytes()

            // Hash file content
            val hashedBytes = MessageDigest
                .getInstance("SHA-256")
                .apply { update(bytes) }
                .digest()

            // Get hex representation of hashed bytes
            val hash = Hex.toHexString(hashedBytes)

            // Return the hash
            setResult(
                Activity.RESULT_OK,
                Intent().putExtra("hash", hash)
            )
            finish()
        }
    }
}
