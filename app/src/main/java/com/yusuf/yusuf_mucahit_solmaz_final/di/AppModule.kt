package com.yusuf.yusuf_mucahit_solmaz_final.di

import android.content.Context
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.network.ApiService
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getProductUseCase.GetProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideGetProductUseCase(apiService: ApiService): GetProductUseCase {
        return GetProductUseCase(apiService)
    }

}