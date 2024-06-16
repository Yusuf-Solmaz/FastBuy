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
import androidx.navigation.NavController
import com.yusuf.yusuf_mucahit_solmaz_final.R
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage.viewModel.AuthViewModel
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.ButtonComponent
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.ClickableLoginTextComponent
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.DividerTextComponent
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.HeadingTextComponent
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.Loader
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.PasswordFieldComponent
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.TextFieldComponent

@Composable
fun SignUpScreen(
    navController: NavController,
    onLoginClick: () -> Unit = {},
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current


    LaunchedEffect(uiState.error) {
        uiState.error?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }


    LaunchedEffect(uiState.user) {
        uiState.user?.let {
            navController.navigate("login_screen") {
                popUpTo("login_screen") {
                    inclusive = true }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp)
    ) {
        if (uiState.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

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

                Spacer(modifier = Modifier.height(20.dp))
                HeadingTextComponent("Create an Account")
                Spacer(modifier = Modifier.height(20.dp))

                TextFieldComponent(
                    stateValue = email,
                    onValueChange = { updatedEmail ->
                        email = updatedEmail.trim()
                    },
                    label = "Email",
                    painterResource = painterResource(id = R.drawable.mail_icon)
                )

                PasswordFieldComponent(
                    stateValue = password,
                    onValueChange = { updatedPassword ->
                        password = updatedPassword.trim()
                    },
                    label = "Password",
                    painterResource = painterResource(id = R.drawable.lock_icon)
                )

                Spacer(modifier = Modifier.height(70.dp))
                ButtonComponent(value = "Register", onClick = {
                    viewModel.signUp(email, password)
                })
                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent()

                ClickableLoginTextComponent(tryToLogin = true, onTextSelected = {
                    onLoginClick()
                    navController.navigate("login_screen")
                })
            }
        }
    }
}
