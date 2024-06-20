package com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage.state


import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.auth.LoginResponse

data class AuthState(
    val isLoading: Boolean = false,
    val user: LoginResponse? = null,
    val error: String? = null,
)