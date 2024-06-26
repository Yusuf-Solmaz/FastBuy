package com.yusuf.yusuf_mucahit_solmaz_final.data.remote.network

import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.auth.LoginRequest
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.auth.LoginResponse
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.cart.AddCartRequest
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.cart.AddCartResponse
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.category.RootCategoryResponse
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.Product
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.RootProductResponse
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.profile.RootProfile
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.profile.UpdateUserProfileRequest
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.userCart.RootUserCart
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int, @Query("skip") skip: Int): Response<RootProductResponse>

    @GET("products/categories")
    suspend fun getProductsCategory(): Response<RootCategoryResponse>

    @GET("products/category/{categoryName}")
    suspend fun getProductsByCategory(@Path("categoryName") category: String): Response<RootProductResponse>

    @GET("products/{id}")
    suspend fun getProductsById(@Path("id") id: String): Response<Product>

    @Headers("Content-Type: application/json")
    @POST("carts/add")
    suspend fun addToCart(@Body request: AddCartRequest): Response<AddCartResponse>

    @GET("users/{userId}/carts")
    suspend fun getUserCartByUserId(@Path("userId") userId: Int): Response<RootUserCart>

    @GET("users/{userId}")
    suspend fun getUserProfile(@Path("userId") userId: Int): Response<RootProfile>

    @Headers("Content-Type: application/json")
    @PUT("users/{userId}")
    suspend fun updateUserProfile(
        @Path("userId") userId: Int,
        @Body request: UpdateUserProfileRequest
    ): Response<RootProfile>

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("auth/me")
    suspend fun getCurrentUser(@Header("Authorization") token: String): Response<RootProfile>

    @GET("products/search")
    suspend fun searchProduct(@Query("q") query: String): Response<RootProductResponse>

    @DELETE("carts/{id}")
    suspend fun deleteCart(@Path("id") cartId: Int): Response<Unit>

}
