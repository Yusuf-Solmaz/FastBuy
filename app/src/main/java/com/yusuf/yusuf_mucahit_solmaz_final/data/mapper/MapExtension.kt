package com.yusuf.yusuf_mucahit_solmaz_final.data.mapper

import com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.model.CurrentUser
import com.yusuf.yusuf_mucahit_solmaz_final.data.local.model.FavoriteProducts
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.auth.LoginResponse
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.cart.AddCartRequest
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.cart.CartProduct
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.Product
import java.text.SimpleDateFormat
import java.util.*

fun String.formatDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return try {
        val date = inputFormat.parse(this)
        if (date != null) {
            outputFormat.format(date)
        } else {
            this
        }
    } catch (e: Exception) {
        this
    }
}

fun Product.toFavoriteProduct(): FavoriteProducts {
    return FavoriteProducts(
        productId = this.id,
        productImage = this.images[0],
        rating = this.rating,
        title = this.title,
        description = this.description,
        price = this.price,
        shippingInformation = this.shippingInformation
    )
}

fun Product.toAddCartRequest(userId:Int,quantity: String): AddCartRequest {
    return AddCartRequest(
        userId= userId,
        products = listOf(
          CartProduct(
                id = this.id,
                quantity = quantity
            )
    )
    )
}

fun LoginResponse.toCurrentUser(): CurrentUser{
    return CurrentUser(
        id = this.id,
        username = this.username,
        token = this.token,
        email = this.email,
        image = this.image,
        refreshToken = this.refreshToken
    )
}