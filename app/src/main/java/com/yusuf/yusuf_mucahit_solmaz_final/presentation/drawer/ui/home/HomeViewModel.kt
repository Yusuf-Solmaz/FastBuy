package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuf.yusuf_mucahit_solmaz_final.core.AppResult.GeneralResult
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.RootProductResponse
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getProductUseCase.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase
) : ViewModel() {

    private val _products = MutableLiveData<ProductState>()
    val products: LiveData<ProductState> = _products

    init {
        getProduct()
    }

    private fun getProduct() {
        _products.value = ProductState(isLoading = true)
        viewModelScope.launch {
            getProductUseCase.getProduct().collect { result ->
                when (result) {
                    is GeneralResult.Error -> {
                        _products.postValue(ProductState(
                            error = result.message,
                            isLoading = false,
                            productResponse = null
                        ))
                    }
                    GeneralResult.Loading -> {
                        _products.postValue(ProductState(
                            isLoading = true,
                            error = null,
                            productResponse = null
                        ))
                    }
                    is GeneralResult.Success -> {
                        _products.postValue(ProductState(
                            isLoading = false,
                            error = null,
                            productResponse = result.data
                        ))
                    }
                }
            }
        }
    }
}