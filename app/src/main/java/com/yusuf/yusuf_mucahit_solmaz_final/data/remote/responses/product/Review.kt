package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product

data class Review(
    val comment: String,
    val date: String,
    val rating: Int,
    val reviewerEmail: String,
    val reviewerName: String
)