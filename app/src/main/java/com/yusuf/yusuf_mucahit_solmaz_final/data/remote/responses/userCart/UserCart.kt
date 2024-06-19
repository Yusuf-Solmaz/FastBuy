package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.userCart

data class UserCart(
    val discountedTotal: Double,
    val id: Int,
    val products: List<Product>,
    val total: Double,
    val totalProducts: Int,
    val totalQuantity: Int,
    val userId: Int
)