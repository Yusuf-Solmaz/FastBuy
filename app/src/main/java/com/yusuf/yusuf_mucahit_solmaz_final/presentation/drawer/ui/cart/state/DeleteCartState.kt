package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.cart.state

data class DeleteCartState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)
