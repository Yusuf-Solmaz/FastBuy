package com.yusuf.yusuf_mucahit_solmaz_final.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yusuf.paparafinalcase.presentation.onBoardingScreen.OnBoardingScreen
import com.yusuf.yusuf_mucahit_solmaz_final.R
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.SplashScreen.SplashScreen
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage.LoginScreen
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.LoadingLottie


@Composable
fun Navigation(mainViewModel: MainViewModel) {
    val navController = rememberNavController()

    LaunchedEffect(mainViewModel.isLoading) {
        if (!mainViewModel.isLoading) {
            navController.navigate(mainViewModel.startDestination) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    if (mainViewModel.isSplashScreenVisible) {
        SplashScreen()
    } else if (mainViewModel.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingLottie(resId = R.raw.loading_anim)
        }
    }

    else {
        NavHost(navController = navController, startDestination = mainViewModel.startDestination) {
            composable("onboarding_screen") {
                OnBoardingScreen(mainViewModel, navController)
            }
            composable("login_screen") {
                LoginScreen()
            }

        }
    }
}