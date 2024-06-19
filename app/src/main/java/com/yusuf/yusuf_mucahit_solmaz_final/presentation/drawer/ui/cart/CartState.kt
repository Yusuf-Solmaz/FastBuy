package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.cart

import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.RootProductResponse
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.userCart.RootUserCart

data class CartState(
    val isLoading: Boolean = false,
    val cartResponse: RootUserCart? = null,
    val error: String? = null
)
