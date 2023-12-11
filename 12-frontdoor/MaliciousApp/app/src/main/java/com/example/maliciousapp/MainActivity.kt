package com.example.maliciousapp

import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    val sUrl = "http://10.0.2.2:8085"
    val urlParameters = "username=testuser&password=passtestuser123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            Log.d("MOBIOTSEC", "The flag is ${getFlag()}")
        } catch (e: Exception) {
            Log.e("MOBIOTSEC", e.toString())
        }
    }

    fun getFlag(): String {
        val postDataLength = urlParameters
            .toByteArray()
            .size
        val conn = (URL("${sUrl}?" + urlParameters).openConnection() as HttpURLConnection)
            .apply {
                setRequestMethod("GET")
                setRequestProperty(
                    "Content-Type",
                    "application/x-www-form-urlencoded"
                )
                setRequestProperty("charset", "utf-8")
                setRequestProperty("Content-Length", postDataLength.toString())
                setUseCaches(false)
            }
        return conn
            .getInputStream()
            .readAllBytes()
            .toString()
    }
}
