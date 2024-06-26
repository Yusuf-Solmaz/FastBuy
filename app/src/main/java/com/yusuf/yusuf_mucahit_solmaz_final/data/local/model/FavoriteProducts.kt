package com.yusuf.yusuf_mucahit_solmaz_final.data.local.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class FavoriteProducts(
    @PrimaryKey(autoGenerate = true)
    var id :Int = 0,
    var productId: Int,
    var productImage: String,
    var rating: Double,
    var title: String,
    var description: String,
    var price: Double,
    var shippingInformation: String
)
