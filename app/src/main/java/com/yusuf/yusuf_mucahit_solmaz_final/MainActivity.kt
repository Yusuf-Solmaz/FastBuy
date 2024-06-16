package com.yusuf.yusuf_mucahit_solmaz_final

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.yusuf.yusuf_mucahit_solmaz_final.navigation.MainViewModel
import com.yusuf.yusuf_mucahit_solmaz_final.navigation.Navigation
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage.SignUpScreen
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
