package com.yusuf.yusuf_mucahit_solmaz_final.core.utils

import android.content.Context
import android.content.res.Configuration

import android.util.Log
import java.util.Locale

object AppUtils {
    const val BASE_URL = "https://dummyjson.com/"

    fun getAppLocale(context: Context): Configuration{
        val sharedPref = context.getSharedPreferences("com.yusuf.yusuf_mucahit_solmaz_final", Context.MODE_PRIVATE)
        val languageCode = sharedPref.getString("SELECTED_LANGUAGE", "en")

        val locale = Locale(languageCode ?: "en")
        Log.d("languageCode", "updateBaseContextLocale: $languageCode")
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        return config
    }
}