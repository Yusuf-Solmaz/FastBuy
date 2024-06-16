package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getProductUseCase

import com.yusuf.yusuf_mucahit_solmaz_final.core.rootFlow.rootFlow
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.network.ApiService
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val apiService: ApiService) {

    fun getProduct() = rootFlow {
        apiService.getProducts()
    }

}