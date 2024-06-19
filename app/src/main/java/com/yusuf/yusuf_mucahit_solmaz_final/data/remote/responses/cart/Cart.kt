package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.cart

data class AddCartRequest(
    val userId: Int = 1,
    val products: List<CartProduct>
)

data class CartProduct(
    val id: Int,
    val quantity: String
)

data class AddCartResponse(
    val id: Int,
    val products: List<CartProductResponse>,
    val total: Double,
    val discountedTotal: Double,
    val userId: Int,
    val totalProducts: Int,
    val totalQuantity: Int
)

data class CartProductResponse(
    val id: Int,
    val title: String,
    val price: Double,
    val quantity: Int,
    val total: Double,
    val discountPercentage: Double,
    val discountedPrice: Double,
    val thumbnail: String
)
