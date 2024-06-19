package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getUserProfile

import com.yusuf.yusuf_mucahit_solmaz_final.core.rootFlow.rootFlow
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.network.ApiService
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val apiService: ApiService
) {
    fun getUserProfile(userId: Int = 6) = rootFlow {
        apiService.getUserProfile(userId)
    }
}