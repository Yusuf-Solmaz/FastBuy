package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.auth

data class LoginResponse(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val image: String,
    val token: String,
    val refreshToken: String
)
