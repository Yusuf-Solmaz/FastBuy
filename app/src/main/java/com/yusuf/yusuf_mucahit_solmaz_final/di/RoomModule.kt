package com.yusuf.yusuf_mucahit_solmaz_final.di

import android.content.Context
import androidx.room.Room
import com.yusuf.yusuf_mucahit_solmaz_final.data.local.dao.FavoriteProductsDao
import com.yusuf.yusuf_mucahit_solmaz_final.data.local.db.FavoriteProductDatabase
import com.yusuf.yusuf_mucahit_solmaz_final.data.local.model.FavoriteProducts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {


    @Provides
    @Singleton
    fun providesFavoriteProductsDao(db: FavoriteProductDatabase): FavoriteProductsDao{
        return db.favoriteProductsDao()
    }

    @Provides
    @Singleton
    fun providesFoodDatabase(
        @ApplicationContext context: Context
    ): FavoriteProductDatabase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            klass = FavoriteProductDatabase::class.java,
            name = "favorite_products.db"
        ).build()
    }
}