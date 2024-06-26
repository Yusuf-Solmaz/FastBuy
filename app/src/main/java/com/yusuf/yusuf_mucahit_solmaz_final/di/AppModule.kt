package com.yusuf.yusuf_mucahit_solmaz_final.di


import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.network.ApiService
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.userUseCases.addToCart.AddToCartUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.userUseCases.deleteCart.DeleteCartUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.userUseCases.getCurrentUser.GetCurrentUserUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.productUseCases.getProductById.GetProductByIdUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.productUseCases.getProductUseCase.GetProductUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.productUseCases.getProductsByCategory.GetProductsByCategoryUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.productUseCases.getProductsCategory.GetProductsCategoryUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.userUseCases.getUserCart.GetUserCartUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.userUseCases.getUserProfile.GetUserProfileUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.authUseCases.login.LoginUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.productUseCases.searchProduct.SearchProductUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.userUseCases.updateUserProfile.UpdateUserProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent



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
    fun addToCart(apiService: ApiService): AddToCartUseCase {
        return AddToCartUseCase(apiService)
    }

    @Provides
    fun getUserCard(apiService: ApiService): GetUserCartUseCase {
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

    @Provides
    fun searchProducts(apiService: ApiService): SearchProductUseCase {
        return SearchProductUseCase(apiService)
    }

    @Provides
    fun providesDeleteCart(apiService: ApiService): DeleteCartUseCase {
        return DeleteCartUseCase(apiService)
    }
}