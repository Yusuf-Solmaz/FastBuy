package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.searchProduct

import com.yusuf.yusuf_mucahit_solmaz_final.core.rootFlow.rootFlow
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.network.ApiService
import javax.inject.Inject

class SearchProductUseCase @Inject constructor(
    private val apiService: ApiService
) {

    fun searchProduct(q:String) = rootFlow {
        apiService.searchProduct(q)
    }
}