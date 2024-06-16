package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.category

import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.category.RootCategoryResponse


data class CategoryState (
        val isLoading: Boolean = false,
        val productResponse: RootCategoryResponse? = null,
        val error: String? = null
    )
