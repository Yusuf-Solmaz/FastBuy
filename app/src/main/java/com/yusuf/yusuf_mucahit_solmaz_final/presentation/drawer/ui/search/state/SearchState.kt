package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.search.state

import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.RootProductResponse

data class SearchState(
    val isLoading: Boolean = false,
    val productResponse: RootProductResponse? = null,
    val error: String? = null
)
