package com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig

import android.content.Context
import android.graphics.Color
import android.view.View
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

object RemoteConfigManager {
    private val remoteConfig: FirebaseRemoteConfig by lazy {
        FirebaseRemoteConfig.getInstance().apply {
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build()
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(mapOf(
                "background_color" to "#FFFFFF"
            ))
        }
    }

    private fun fetchRemoteConfig(onComplete: (Boolean) -> Unit) {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }

    private fun getBackgroundColor(): String {
        return remoteConfig.getString("background_color")
    }

    fun setBackgroundColor(color: String, onComplete: (Boolean) -> Unit) {
        remoteConfig.setDefaultsAsync(mapOf("background_color" to color))
            .addOnCompleteListener { task ->
                fetchRemoteConfig { success ->
                    onComplete(success)
                }
            }
    }

    fun updateUI(view: View) {
        val backgroundColor = getBackgroundColor()

        view.setBackgroundColor(Color.parseColor(backgroundColor))
    }

    fun saveBackgroundColor(color: String,context: Context) {
        val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("background_color", color)
        editor.apply()
    }

    fun loadBackgroundColor(context: Context, changeBackground: (color: String) ->Unit) {
        val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val color = sharedPreferences.getString("background_color", "#FFFFFF")

        changeBackground(color ?: "#FFFFFF")
    }

}

