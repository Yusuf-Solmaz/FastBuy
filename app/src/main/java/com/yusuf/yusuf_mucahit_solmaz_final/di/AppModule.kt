package com.yusuf.yusuf_mucahit_solmaz_final.di

import android.content.Context
import com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.SessionManager
import com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.repo.UserSessionRepository
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.network.ApiService
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.addToCart.AddToCartUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getCurrentUser.GetCurrentUserUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getProductById.GetProductByIdUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getProductUseCase.GetProductUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getProductsByCategory.GetProductsByCategoryUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getProductsCategory.GetProductsCategoryUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getUserCart.GetUserCartUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getUserProfile.GetUserProfileUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.login.LoginUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.updateUserProfile.UpdateUserProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideGetProductUseCase(apiService: ApiService): GetProductUseCase {
        return GetProductUseCase(apiService)
    }

    @Provides
    fun provideGetProductsCategoryUseCase(apiService: ApiService): GetProductsCategoryUseCase {
        return GetProductsCategoryUseCase(apiService)
    }

    @Provides
    fun getProductsByCategoryUseCase(apiService: ApiService): GetProductsByCategoryUseCase {
        return GetProductsByCategoryUseCase(apiService)
    }

    @Provides
    fun getProductsByIdUseCase(apiService: ApiService): GetProductByIdUseCase {
        return GetProductByIdUseCase(apiService)
    }

    @Provides
    fun addToCart(apiService: ApiService): AddToCartUseCase{
        return AddToCartUseCase(apiService)
    }

    @Provides
    fun getUserCard(apiService: ApiService): GetUserCartUseCase{
        return GetUserCartUseCase(apiService)
    }

    @Provides
    fun getUserProfile(apiService: ApiService): GetUserProfileUseCase {
        return GetUserProfileUseCase(apiService)

    }

    @Provides
    fun updateProfile(apiService: ApiService): UpdateUserProfileUseCase {
        return UpdateUserProfileUseCase(apiService)
    }

    @Provides
    fun login(apiService: ApiService): LoginUseCase {
        return LoginUseCase(apiService)
    }

    @Provides
    fun getCurrentUser(apiService: ApiService): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(apiService)
    }

}