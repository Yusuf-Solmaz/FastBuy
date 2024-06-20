package com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.model

data class CurrentUser(
    val id: Int,
    val username: String,
    val email: String,
    val image: String,
    val token: String,
    val refreshToken: String
)
