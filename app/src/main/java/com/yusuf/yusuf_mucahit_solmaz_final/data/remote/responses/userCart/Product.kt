package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.userCart

data class Product(
    val discountPercentage: Double,
    val discountedTotal: Double,
    val id: Int,
    val price: Double,
    val quantity: Int,
    val thumbnail: String,
    val title: String,
    val total: Double
)