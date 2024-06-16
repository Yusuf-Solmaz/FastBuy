package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.network

import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.category.RootCategoryResponse
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.RootProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("products")
    suspend fun getProducts(): Response<RootProductResponse>


    @GET("products/categories")
    suspend fun getProductsCategory(): Response<RootCategoryResponse>

    @GET("products/category/{categoryName}")
    suspend fun getProductsByCategory(@Path("categoryName") category: String): Response<RootProductResponse>


}