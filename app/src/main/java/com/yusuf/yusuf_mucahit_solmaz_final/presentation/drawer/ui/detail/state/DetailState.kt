package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.detail.state

import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.Product

data class DetailState(
    val isLoading: Boolean = false,
    val productResponse: Product? = null,
    val error: String? = null
)