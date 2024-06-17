package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuf.yusuf_mucahit_solmaz_final.data.local.dao.FavoriteProductsDao
import com.yusuf.yusuf_mucahit_solmaz_final.data.local.model.FavoriteProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteProductsDao: FavoriteProductsDao
) : ViewModel() {

    private val _favoriteProducts = MutableLiveData<List<FavoriteProducts>>()
    val favoriteProducts: LiveData<List<FavoriteProducts>> = _favoriteProducts

    init {
        getFavoriteProducts()
    }

    fun getFavoriteProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val products = favoriteProductsDao.getAll()
            _favoriteProducts.postValue(products)
        }
    }
}
