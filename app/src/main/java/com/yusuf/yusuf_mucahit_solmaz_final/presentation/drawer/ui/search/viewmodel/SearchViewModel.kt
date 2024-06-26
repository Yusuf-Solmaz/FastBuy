package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuf.yusuf_mucahit_solmaz_final.core.AppResult.GeneralResult
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.cart.AddCartRequest
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.userUseCases.addToCart.AddToCartUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.productUseCases.searchProduct.SearchProductUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.search.state.SearchAddToCartState
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.search.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductUseCase: SearchProductUseCase,
    private val addToCartUseCase: AddToCartUseCase
): ViewModel() {

    private val _searchProduct = MutableLiveData<SearchState>()
    val searchProduct: LiveData<SearchState> = _searchProduct

    private val _searchAddToCart = MutableLiveData<SearchAddToCartState>()
    val searchAddToCart: LiveData<SearchAddToCartState> = _searchAddToCart

    fun searchProducts(query: String) {
        _searchProduct.value = SearchState(isLoading = true)
        viewModelScope.launch {
            searchProductUseCase.searchProduct(query).collect { result ->

                when (result) {
                    is GeneralResult.Error -> {
                        _searchProduct.postValue(SearchState(
                            error = result.message,
                            isLoading = false,
                            productResponse = null))
                }
                    is GeneralResult.Loading -> {
                        _searchProduct.postValue(SearchState(
                            isLoading = true,
                            error = null,
                            productResponse = null))
                    }
                    is GeneralResult.Success -> {
                        _searchProduct.postValue(SearchState(
                            isLoading = false,
                            error = null,
                            productResponse = result.data))
            }
        }
    }
}
        }
    fun addToCart(addCartRequest: AddCartRequest) {
        _searchAddToCart.value = SearchAddToCartState(isLoading = true)

        viewModelScope.launch {
            addToCartUseCase.addToCart(addCartRequest).collect { result ->
                when (result) {
                    is GeneralResult.Error -> {

                        _searchAddToCart.postValue(
                            SearchAddToCartState(
                                isLoading = false,
                                success = false,
                                error = result.message
                            )
                        )
                    }
                    GeneralResult.Loading -> {

                        _searchAddToCart.postValue(
                            SearchAddToCartState(
                                isLoading = true
                            )
                        )
                    }
                    is GeneralResult.Success -> {

                        _searchAddToCart.postValue(
                            SearchAddToCartState(
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