package com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage

import android.content.Intent
import android.util.Log
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
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage.viewModel.AuthViewModel
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.ButtonComponent

import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.Loader
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.PasswordFieldComponent
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.TextFieldComponent
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.components.UnderLinedTextComponent
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.TransactionsActivity

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val authState by viewModel.authState.collectAsState()
    val sessionState by viewModel.sessionState.collectAsState()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(sessionState.user) {
        sessionState.user?.let {
            val intent = Intent(context, TransactionsActivity::class.java)
            context.startActivity(intent)
        }
    }


    if  (authState.error != null){
        Toast.makeText(context, context.getString(R.string.login_error), Toast.LENGTH_SHORT).show()
    }

    if (authState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Loader(resId = R.raw.loading_anim)
        }
    }
    else {
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
                    label = context.getString(R.string.nickname),
                    painterResource = painterResource(id = R.drawable.ic_profile)
                )
                PasswordFieldComponent(
                    password,
                    label = context.getString(R.string.password),
                    onValueChange = { updatedPassword -> password = updatedPassword.trim() },
                    painterResource(id = R.drawable.lock_icon)
                )

                Spacer(modifier = Modifier.height(10.dp))
                ButtonComponent(value = context.getString(R.string.loginButton), onClick = {
                    viewModel.login(email, password)
                })



            }
        }
    }
}
