package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.userUseCases.deleteCart

import com.yusuf.yusuf_mucahit_solmaz_final.core.rootFlow.rootFlow
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.network.ApiService
import javax.inject.Inject

class DeleteCartUseCase @Inject constructor(private val apiService: ApiService) {

    fun deleteCart(cartId:Int) = rootFlow {
        apiService.deleteCart(cartId)
    }
}