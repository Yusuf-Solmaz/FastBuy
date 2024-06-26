package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.userUseCases.addToCart

import com.yusuf.yusuf_mucahit_solmaz_final.core.rootFlow.rootFlow
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.network.ApiService
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.cart.AddCartRequest
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(private val apiService: ApiService){

    fun addToCart(addCartRequest: AddCartRequest) = rootFlow {
        apiService.addToCart(addCartRequest)
    }


}