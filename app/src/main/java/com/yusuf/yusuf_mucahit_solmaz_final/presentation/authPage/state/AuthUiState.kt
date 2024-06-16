package com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage.state

import com.google.firebase.auth.FirebaseUser

data class AuthUiState(
    val isLoading: Boolean = false,
    val user: FirebaseUser? = null,
    val error: String? = null,
)