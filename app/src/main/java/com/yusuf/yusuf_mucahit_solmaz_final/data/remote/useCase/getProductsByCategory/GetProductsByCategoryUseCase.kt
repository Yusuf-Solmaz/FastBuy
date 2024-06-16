package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getProductsByCategory

import com.yusuf.yusuf_mucahit_solmaz_final.core.rootFlow.rootFlow
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.network.ApiService
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(private val apiService: ApiService){

    fun getProductsByCategory(category: String) = rootFlow {
        apiService.getProductsByCategory(category)
    }

}