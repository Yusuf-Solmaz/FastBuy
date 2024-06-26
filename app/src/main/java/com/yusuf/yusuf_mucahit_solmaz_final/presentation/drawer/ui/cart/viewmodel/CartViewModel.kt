package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.cart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuf.yusuf_mucahit_solmaz_final.core.AppResult.GeneralResult
import com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.repo.UserSessionRepository
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.userUseCases.deleteCart.DeleteCartUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.userUseCases.getUserCart.GetUserCartUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.cart.state.CartState
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.cart.state.DeleteCartState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getUserCartUseCase: GetUserCartUseCase,
    private val session: UserSessionRepository,
    private val deleteCartUseCase: DeleteCartUseCase
) : ViewModel() {

    private val _cart = MutableLiveData<CartState>()
    val cart: LiveData<CartState> = _cart

    private val _deleteCart = MutableLiveData<DeleteCartState>()
    val deleteCart: LiveData<DeleteCartState> = _deleteCart

    fun getUserCart() {
        _cart.value = CartState(isLoading = true)

        viewModelScope.launch {
            getUserCartUseCase.getUserCardById(session.getUserId()).collect { result ->
                when (result) {
                    is GeneralResult.Error -> {
                        _cart.postValue(
                            CartState(
                                error = result.message,
                                isLoading = false,
                                cartResponse = null
                            )
                        )
                    }

                    GeneralResult.Loading -> {
                        _cart.postValue(
                            CartState(
                                isLoading = true,
                                error = null,
                                cartResponse = null
                            )
                        )
                    }

                    is GeneralResult.Success -> {
                        _cart.postValue(
                            CartState(
                                isLoading = false,
                                error = null,
                                cartResponse = result.data
                            )
                        )
                    }
                }
            }

        }
    }

    fun deleteCart() {
        _deleteCart.value = DeleteCartState(isLoading = true)
        viewModelScope.launch {
            deleteCartUseCase.deleteCart(session.getUserId()).collect { result ->
                when (result) {
                    is GeneralResult.Error -> {
                        _deleteCart.postValue(
                            DeleteCartState(
                                error = result.message,
                                isLoading = false,
                                success = false
                            )
                        )
                    }

                    GeneralResult.Loading -> {
                        _deleteCart.postValue(
                            DeleteCartState(
                                isLoading = true,
                                error = null,
                                success = false
                            )
                        )
                    }

                    is GeneralResult.Success -> {
                        _deleteCart.postValue(
                            DeleteCartState(
                                isLoading = false,
                                error = null,
                                success = true
                            )
                        )
                    }
                }
            }
        }
    }
}
