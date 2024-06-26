package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.favorites.state

import com.yusuf.yusuf_mucahit_solmaz_final.data.local.model.FavoriteProducts

data class FavoriteState(
    val isLoading: Boolean = false,
    val favoriteProductsResponse: List<FavoriteProducts>? = null,
    val error: String? = null
)