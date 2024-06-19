package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.profile

data class Bank(
    val cardExpire: String,
    val cardNumber: String,
    val cardType: String,
    val currency: String,
    val iban: String
)