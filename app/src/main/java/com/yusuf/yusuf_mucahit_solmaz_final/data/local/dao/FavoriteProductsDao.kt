package com.yusuf.yusuf_mucahit_solmaz_final.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yusuf.yusuf_mucahit_solmaz_final.data.local.model.FavoriteProducts

@Dao
interface FavoriteProductsDao {

    @Query("SELECT * FROM favorite_products")
    fun getAll(): List<FavoriteProducts>

    @Query("DELETE FROM favorite_products WHERE productId = :productId")
    suspend fun deleteProduct(productId: Int)

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_products WHERE productId = :productId)")
    suspend fun isProductFavorite(productId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: FavoriteProducts)


}