package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.userUseCases.getCurrentUser

import com.yusuf.yusuf_mucahit_solmaz_final.core.rootFlow.rootFlow
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.network.ApiService
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(private val apiService: ApiService) {

    fun getCurrentUser(token:String) = rootFlow {
        apiService.getCurrentUser(token)
    }
}