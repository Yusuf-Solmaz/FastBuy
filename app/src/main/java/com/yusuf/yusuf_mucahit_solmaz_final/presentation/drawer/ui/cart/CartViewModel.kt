package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuf.yusuf_mucahit_solmaz_final.core.AppResult.GeneralResult
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getUserCart.GetUserCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getUserCartUseCase: GetUserCartUseCase,
) : ViewModel() {

    private val _cart = MutableLiveData<CartState>()
    val cart: LiveData<CartState> = _cart

    fun getUserCart() {
        _cart.value = CartState(isLoading = true)
        viewModelScope.launch {
            getUserCartUseCase.getUserCardById().collect { result ->
                when(result){
                    is GeneralResult.Error -> {
                        _cart.postValue(CartState(
                            error = result.message,
                            isLoading = false,
                            cartResponse = null
                        ))
                    }
                    GeneralResult.Loading -> {
                        _cart.postValue(CartState(
                            isLoading = true,
                            error = null,
                            cartResponse = null))
                }
                    is GeneralResult.Success -> {
                        Log.d("getUserCart", "getUserCart: ${result.data}")
                        _cart.postValue(CartState(
                            isLoading = false,
                            error = null,
                            cartResponse = result.data))
            }
        }
    }

        }
    }
}
