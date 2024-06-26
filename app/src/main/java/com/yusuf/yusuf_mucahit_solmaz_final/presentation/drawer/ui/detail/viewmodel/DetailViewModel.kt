package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuf.yusuf_mucahit_solmaz_final.core.AppResult.GeneralResult
import com.yusuf.yusuf_mucahit_solmaz_final.data.local.dao.FavoriteProductsDao
import com.yusuf.yusuf_mucahit_solmaz_final.data.local.model.FavoriteProducts
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.cart.AddCartRequest
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.userUseCases.addToCart.AddToCartUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.productUseCases.getProductById.GetProductByIdUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.detail.state.AddToCartState
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.detail.state.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val favoriteProductsDao: FavoriteProductsDao,
    private val addToCartUseCase: AddToCartUseCase
): ViewModel() {

    private val _productDetail = MutableLiveData<DetailState>()
    val productDetail: LiveData<DetailState> = _productDetail

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _addToCartState = MutableLiveData<AddToCartState>()
    val addToCartState: LiveData<AddToCartState> = _addToCartState

    fun getProductById(id: String,onError: (String) -> Unit) {
        _productDetail.value = DetailState(isLoading = true)

        viewModelScope.launch {
            getProductByIdUseCase.getProductById(id).collect { result ->
                when (result) {
                    is GeneralResult.Error -> {

                        _productDetail.postValue(
                            DetailState(
                                error = result.message,
                                isLoading = false,
                                productResponse = null
                            )
                        )
                    }

                    GeneralResult.Loading -> {

                        _productDetail.postValue(
                            DetailState(
                                isLoading = true,
                                error = null,
                                productResponse = null
                            )
                        )
                    }

                    is GeneralResult.Success -> {

                        _productDetail.postValue(
                            DetailState(
                                isLoading = false,
                                error = null,
                                productResponse = result.data
                            )
                        )

                        checkIfFavorite(id, onError)
                    }
                }
            }
        }
    }

    private fun checkIfFavorite(id: String,onError: (String) -> Unit) {
        try {
            viewModelScope.launch {
                _isFavorite.value = favoriteProductsDao.isProductFavorite(id.toInt())
            }
        }
        catch (e:Exception){
            e.localizedMessage?.let {
                onError(it)
            }
        }


    }

    fun addOrRemoveFavorite(product: FavoriteProducts,onError: (String) -> Unit) {

        try {
            viewModelScope.launch {
                if (_isFavorite.value == true) {
                    favoriteProductsDao.deleteProduct(product.productId)
                } else {
                    favoriteProductsDao.insertProduct(product)
                }
                checkIfFavorite(product.productId.toString(), onError)
            }
        }
        catch (e:Exception){
            e.localizedMessage?.let {
                onError(it)
            }
        }

    }

    fun addToCart(addCartRequest: AddCartRequest) {
        _addToCartState.value = AddToCartState(isLoading = true)

        viewModelScope.launch {
            addToCartUseCase.addToCart(addCartRequest).collect { result ->
                when (result) {
                    is GeneralResult.Error -> {

                        _addToCartState.postValue(
                            AddToCartState(
                                isLoading = false,
                                success = false,
                                error = result.message
                            )
                        )
                    }
                    GeneralResult.Loading -> {

                        _addToCartState.postValue(
                            AddToCartState(
                                isLoading = true
                            )
                        )
                    }
                    is GeneralResult.Success -> {

                        _addToCartState.postValue(
                            AddToCartState(
                                isLoading = false,
                                success = true,
                                error = null
                            )
                        )
                    }
                }
            }
        }
    }

}