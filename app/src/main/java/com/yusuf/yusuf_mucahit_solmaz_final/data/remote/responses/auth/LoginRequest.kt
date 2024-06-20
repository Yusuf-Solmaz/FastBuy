package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.auth

data class LoginRequest(
    val username: String,
    val password: String,
    val expiresInMins: Int = 30
)