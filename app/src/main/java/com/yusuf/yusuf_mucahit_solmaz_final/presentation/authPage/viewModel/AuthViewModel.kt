package com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuf.yusuf_mucahit_solmaz_final.core.AppResult.GeneralResult
import com.yusuf.yusuf_mucahit_solmaz_final.data.auth.service.AuthService
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage.state.AuthUiState
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage.state.LoggingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthService,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _loggingState = MutableStateFlow(LoggingState())
    val loggingState: StateFlow<LoggingState> = _loggingState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            authRepository.signInWithEmailAndPassword(email, password).collect { result ->
                when (result) {
                    is GeneralResult.Success -> {
                        result.data?.let { user ->
                            _uiState.value = _uiState.value.copy(user = user, isLoading = false)
                        }
                    }

                    is GeneralResult.Error -> {
                        _uiState.value =
                            _uiState.value.copy(error = result.message, isLoading = false)
                    }

                    GeneralResult.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            authRepository.signUpWithEmailAndPassword(email, password)
                .collect { result ->
                    when (result) {
                        is GeneralResult.Success -> {
                            result.data?.let { user ->
                                _uiState.value = _uiState.value.copy(
                                    user = user,
                                    isLoading = false
                                )
                            }
                        }

                        is GeneralResult.Error -> {
                            _uiState.value =
                                _uiState.value.copy(error = result.message, isLoading = false)
                        }

                        GeneralResult.Loading -> {
                            _uiState.value = _uiState.value.copy(isLoading = true)
                        }
                    }
                }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            authRepository.signOut().collect { result ->
                when (result) {
                    is GeneralResult.Success -> {
                        _uiState.value = _uiState.value.copy(
                            user = null,
                            isLoading = false
                        )
                    }

                    is GeneralResult.Error -> {
                        _uiState.value =
                            _uiState.value.copy(error = result.message, isLoading = false)
                    }

                    GeneralResult.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                }
            }
        }
    }



    fun isLoggedIn() {
        viewModelScope.launch {
            authRepository.isLoggedIn().collect { isLoggedIn ->
                _loggingState.value = _loggingState.value.copy(isLoading = true)
                when (isLoggedIn) {
                    is GeneralResult.Success -> {
                        _loggingState.value = _loggingState.value.copy(
                            isLoading = false,
                            transaction = isLoggedIn.data ?: false
                        )
                    }
                    is GeneralResult.Error -> {
                        _loggingState.value = _loggingState.value.copy(
                            isLoading = false,
                            error = isLoggedIn.message
                        )
                    }
                    GeneralResult.Loading -> {
                        _loggingState.value = _loggingState.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
}