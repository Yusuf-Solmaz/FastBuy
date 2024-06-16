package com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage.state

data class LoggingState(
    val transaction: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)