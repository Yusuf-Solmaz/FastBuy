package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.network

import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.RootProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun getProducts(

    ): Response<RootProductResponse>


}