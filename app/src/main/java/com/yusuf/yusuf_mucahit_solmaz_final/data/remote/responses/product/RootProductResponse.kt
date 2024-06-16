package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product

data class RootProductResponse(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)