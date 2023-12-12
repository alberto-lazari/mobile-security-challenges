package com.example.maliciousapp

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val TAG = "MOBIOTSEC"
    private val sUrl = "http://10.0.2.2:8085"
    private val urlParameters = "username=testuser&password=passtestuser123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val handler = Handler()
        Thread(object : Runnable {
            override fun run() {
                val flag = getFlag()
                handler.post(object : Runnable {
                    override fun run() {
                       findViewById<TextView>(R.id.debug_text).text = flag
                   }
                })
            }
        }).start()
    }

    fun getFlag(): String {
        val postDataLength = urlParameters
            .toByteArray()
            .size
        val url = URL("${sUrl}?" + urlParameters)

        try {
            val inputStream = (url.openConnection() as HttpURLConnection)
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
                .getInputStream()
            val input = BufferedReader(InputStreamReader(inputStream))
                .lines()
                .reduce("") { acc, it -> acc + "\n" + it }
            val flag = Regex("(FLAG\\{.*\\})")
                .find(input)
                ?.value ?: "[null]"

            Log.d(TAG, "The flag is $flag")
            return flag
        } catch (e: Exception) {
            val error = "Error: ${ e.toString() }"
            Log.e(TAG, error)
            return error
        }
    }
}
