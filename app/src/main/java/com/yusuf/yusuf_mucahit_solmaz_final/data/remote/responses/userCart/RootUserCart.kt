package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.userCart

data class RootUserCart(
    val carts: List<UserCart>,
    val limit: Int,
    val skip: Int,
    val total: Int
)