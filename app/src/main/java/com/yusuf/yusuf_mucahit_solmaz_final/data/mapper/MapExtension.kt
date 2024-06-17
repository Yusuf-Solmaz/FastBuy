package com.yusuf.yusuf_mucahit_solmaz_final.data.mapper

import com.yusuf.yusuf_mucahit_solmaz_final.data.local.model.FavoriteProducts
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
        description = this.description
    )
}