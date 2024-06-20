package com.yusuf.yusuf_mucahit_solmaz_final


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.yusuf.yusuf_mucahit_solmaz_final.navigation.MainViewModel
import com.yusuf.yusuf_mucahit_solmaz_final.navigation.Navigation
import com.yusuf.yusuf_mucahit_solmaz_final.ui.theme.Yusuf_Mucahit_Solmaz_FinalTheme
import dagger.hilt.android.AndroidEntryPoint

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
}
