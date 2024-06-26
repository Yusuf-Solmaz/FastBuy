package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.productUseCases.getProductById

import com.yusuf.yusuf_mucahit_solmaz_final.core.rootFlow.rootFlow
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.network.ApiService
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val api : ApiService
) {

    fun getProductById(id:String)  = rootFlow {
        api.getProductsById(id)
    }

}