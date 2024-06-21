package com.yusuf.yusuf_mucahit_solmaz_final


import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.AppUtils.getAppLocale
import com.yusuf.yusuf_mucahit_solmaz_final.navigation.MainViewModel
import com.yusuf.yusuf_mucahit_solmaz_final.navigation.Navigation
import com.yusuf.yusuf_mucahit_solmaz_final.ui.theme.Yusuf_Mucahit_Solmaz_FinalTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Yusuf_Mucahit_Solmaz_FinalTheme {
                Navigation(mainViewModel = mainViewModel)

            }
        }
    }

        override fun attachBaseContext(newBase: Context) {
            val config = getAppLocale(newBase)
            super.attachBaseContext(newBase.createConfigurationContext(config))
    }
}
