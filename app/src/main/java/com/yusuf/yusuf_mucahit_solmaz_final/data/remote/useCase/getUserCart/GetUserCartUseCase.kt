package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getUserCart

import com.yusuf.yusuf_mucahit_solmaz_final.core.rootFlow.rootFlow
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.network.ApiService
import javax.inject.Inject

class GetUserCartUseCase @Inject constructor(
    private val apiService: ApiService
) {

    fun getUserCardById(userId: Int) = rootFlow {
        apiService.getUserCartByUserId(userId)
    }

}