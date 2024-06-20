package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuf.yusuf_mucahit_solmaz_final.core.AppResult.GeneralResult
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getProductUseCase.GetProductUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getProductsByCategory.GetProductsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase
) : ViewModel() {

    private val _products = MutableLiveData<ProductState>()
    val products: LiveData<ProductState> = _products




     fun getProduct() {
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

    fun getProductsByCategory(category: String){
        _products.value = ProductState(isLoading = true)
        viewModelScope.launch {
            getProductsByCategoryUseCase.getProductsByCategory(category).collect { result ->
                when(result){
                    is GeneralResult.Error -> {
                        Log.d("getProductsByCategory", "getProductsByCategory: ${result.message}")
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
                        Log.d("getProductsByCategory", "getProductsByCategory: ${result.data}")
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