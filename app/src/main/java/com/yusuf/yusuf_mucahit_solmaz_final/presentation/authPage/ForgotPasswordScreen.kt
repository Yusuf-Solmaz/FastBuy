package com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yusuf.yusuf_mucahit_solmaz_final.R
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage.viewModel.ForgotPasswordViewModel
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.ButtonComponent
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.Loader
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.TextFieldComponent

@Composable
fun ForgotPasswordScreen(
    navController: NavHostController,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(uiState.error) {
        uiState.error?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }


    LaunchedEffect(uiState.transaction) {
        if (uiState.transaction) {
            Toast.makeText(context, "Email Sent, Check Your Email", Toast.LENGTH_SHORT).show()
            navController.navigate("login_screen") {
                popUpTo("login_screen") {
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.padding(18.dp)
    ) {
        if (uiState.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Loader(resId = R.raw.loading_anim)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Loader(resId = R.raw.forgot_password_anim, height = 260.dp)

                Spacer(modifier = Modifier.height(50.dp))

                TextFieldComponent(
                    email,
                    onValueChange = { updatedEmail ->
                        email = updatedEmail
                    },
                    label = "Email",
                    painterResource = painterResource(id = R.drawable.mail_icon)
                )

                Spacer(modifier = Modifier.height(30.dp))

                Spacer(modifier = Modifier.height(20.dp))

                ButtonComponent(value = "Reset Password", onClick = {
                    viewModel.sendPasswordResetEmail(email)
                })
            }
        }
    }
}