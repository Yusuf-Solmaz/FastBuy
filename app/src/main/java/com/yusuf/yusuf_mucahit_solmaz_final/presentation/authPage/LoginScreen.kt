package com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage

import android.content.Intent
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yusuf.yusuf_mucahit_solmaz_final.R
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage.viewModel.AuthViewModel
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.ButtonComponent
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.ClickableLoginTextComponent
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.DividerTextComponent
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.Loader
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.PasswordFieldComponent
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.TextFieldComponent
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.UnderLinedTextComponent
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.detailPage.DetailActivity

@Composable
fun LoginScreen(
    navController: NavHostController,
    onSignUpClick: () -> Unit = {},
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val loggingState by viewModel.loggingState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.isLoggedIn()
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(uiState.user) {
        uiState.user?.let {
            val intent = Intent(context, DetailActivity::class.java)
            context.startActivity(intent)

        }
    }

    LaunchedEffect(loggingState.transaction) {
        if (loggingState.transaction) {
            val intent = Intent(context, DetailActivity::class.java)
            context.startActivity(intent)
        }
    }

    if (uiState.isLoading || loggingState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Loader(resId = R.raw.loading_anim)
        }
    } else {
        Scaffold(modifier = Modifier.padding(18.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Loader(R.raw.login_anim, height = 250.dp)
                Spacer(modifier = Modifier.height(10.dp))
                TextFieldComponent(
                    email,
                    onValueChange = { updatedEmail -> email = updatedEmail.trim() },
                    label = "Email",
                    painterResource = painterResource(id = R.drawable.mail_icon)
                )
                PasswordFieldComponent(
                    password,
                    label = "Password",
                    onValueChange = { updatedPassword -> password = updatedPassword.trim() },
                    painterResource(id = R.drawable.lock_icon)
                )
                UnderLinedTextComponent(value = "Forgot your password?", onClick = {
                    navController.navigate("forgot_password_screen")
                })
                Spacer(modifier = Modifier.height(10.dp))
                ButtonComponent(value = "Login", onClick = {
                    viewModel.signIn(email, password)
                })
                Spacer(modifier = Modifier.height(10.dp))
                DividerTextComponent()
                Spacer(modifier = Modifier.height(15.dp))
                ClickableLoginTextComponent(tryToLogin = false, onTextSelected = {
                    onSignUpClick()
                    navController.navigate("sign_up_screen")
                })
            }
        }
    }
}