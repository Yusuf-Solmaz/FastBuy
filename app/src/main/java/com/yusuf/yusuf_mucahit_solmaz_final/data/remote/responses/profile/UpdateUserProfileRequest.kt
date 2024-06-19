package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.profile

data class UpdateUserProfileRequest(
    val name: String?,
    val lastName: String?,
    val email: String?,
    val phone: String?,
    val password: String?
)