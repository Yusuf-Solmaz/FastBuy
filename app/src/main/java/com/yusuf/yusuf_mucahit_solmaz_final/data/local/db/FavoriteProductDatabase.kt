package com.yusuf.yusuf_mucahit_solmaz_final.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yusuf.yusuf_mucahit_solmaz_final.data.local.dao.FavoriteProductsDao
import com.yusuf.yusuf_mucahit_solmaz_final.data.local.model.FavoriteProducts

@Database(entities = [FavoriteProducts::class], version = 1, exportSchema = false)
abstract class FavoriteProductDatabase: RoomDatabase() {

    abstract fun favoriteProductsDao(): FavoriteProductsDao
}