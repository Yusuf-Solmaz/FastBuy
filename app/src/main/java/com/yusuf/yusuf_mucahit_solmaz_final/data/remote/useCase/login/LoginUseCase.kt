package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.login

import com.yusuf.yusuf_mucahit_solmaz_final.core.rootFlow.rootFlow
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.network.ApiService
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.auth.LoginRequest
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val apiService: ApiService) {

    fun login(request: LoginRequest) = rootFlow {
        apiService.login(request)
    }
}