package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuf.yusuf_mucahit_solmaz_final.core.AppResult.GeneralResult
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getProductById.GetProductByIdUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase
): ViewModel() {

    private val _productDetail = MutableLiveData<DetailState>()
    val productDetail: LiveData<DetailState> = _productDetail

    fun getProductById(id:String){
        _productDetail.value = DetailState(isLoading = true)
        viewModelScope.launch {
            getProductByIdUseCase.getProductById(id).collect { result ->
                when(result){
                    is GeneralResult.Error -> {
                        Log.d("getProductById", "getProductById: ${result.message}")
                        _productDetail.postValue(DetailState(
                            error = result.message,
                            isLoading = false,
                            productResponse = null))
                }
                    GeneralResult.Loading -> {
                        Log.d("getProductById", "getProductById: Loading")
                        _productDetail.postValue(DetailState(
                            isLoading = true,
                            error = null,
                            productResponse = null))
            }
                    is GeneralResult.Success -> {
                        Log.d("getProductById", "getProductById: ${result.data}")
                        _productDetail.postValue(DetailState(
                            isLoading = false,
                            error = null,
                            productResponse = result.data))
        }
                }
            }
        }
    }
}