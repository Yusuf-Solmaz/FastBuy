package com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.yusuf.yusuf_mucahit_solmaz_final.core.AppResult.GeneralResult
import com.yusuf.yusuf_mucahit_solmaz_final.data.auth.service.AuthService
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage.state.LoggingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthService
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoggingState())
    val uiState: StateFlow<LoggingState> = _uiState

    fun sendPasswordResetEmail(email: String) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            authRepository.sendPasswordResetEmail(email).collect{
                    result ->
                when(result){
                    is GeneralResult.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is GeneralResult.Success -> {
                        _uiState.value = _uiState.value.copy(transaction = true, isLoading = false)
                    }
                    is GeneralResult.Error -> {
                        _uiState.value = _uiState.value.copy(error = result.message, isLoading = false, transaction = false)
                    }
                }
            }
        }
    }
}