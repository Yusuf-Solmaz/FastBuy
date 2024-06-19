package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.profile

data class Address(
    val address: String,
    val city: String,
    val coordinates: Coordinates,
    val country: String,
    val postalCode: String,
    val state: String,
    val stateCode: String
)