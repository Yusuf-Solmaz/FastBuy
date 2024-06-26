package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuf.yusuf_mucahit_solmaz_final.core.AppResult.GeneralResult
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.Product
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

    private var currentPage = 0
    private var isLastPage = false
    private var isLoading = false

    fun getProduct() {
        if (isLastPage){
            isLoading = false
            resetPagination()
        }

        isLoading = true
        _products.value = ProductState(isLoading = true)

        viewModelScope.launch {
            getProductUseCase.getProduct(30, currentPage * 30).collect { result ->
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
                        if (result.data?.products?.isEmpty() == true) {
                            isLastPage = true
                            isLoading = false
                            resetPagination()
                        } else {
                            currentPage++
                            val currentProducts = _products.value?.productResponse?.products.orEmpty()
                            val updatedProducts : List<Product> = currentProducts + result.data?.products as List<Product>
                            val updatedResponse = result.data.copy(products = updatedProducts)
                            _products.postValue(ProductState(
                                isLoading = false,
                                error = null,
                                productResponse = updatedResponse
                            ))
                        }
                        isLoading = false
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

    private fun resetPagination() {
        currentPage = 0
        isLastPage = false
    }
}
