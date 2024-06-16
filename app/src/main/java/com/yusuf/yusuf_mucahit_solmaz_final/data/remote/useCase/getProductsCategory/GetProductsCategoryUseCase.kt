package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getProductsCategory

import com.yusuf.yusuf_mucahit_solmaz_final.core.rootFlow.rootFlow
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.network.ApiService
import javax.inject.Inject

class GetProductsCategoryUseCase @Inject constructor(private val apiService: ApiService) {

    fun getProductsCategory() = rootFlow {
        apiService.getProductsCategory()
    }

}