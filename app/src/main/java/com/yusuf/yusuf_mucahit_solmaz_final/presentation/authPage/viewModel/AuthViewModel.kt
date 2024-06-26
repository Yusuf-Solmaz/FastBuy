package com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage.viewModel

import android.content.Context
import com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuf.yusuf_mucahit_solmaz_final.core.AppResult.GeneralResult
import com.yusuf.yusuf_mucahit_solmaz_final.data.mapper.toCurrentUser

import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.auth.LoginRequest
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.authUseCases.login.LoginUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage.state.AuthState
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage.state.SessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val sessionManager = SessionManager.getInstance(context)

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    private val _sessionState = MutableStateFlow(SessionState())
    val sessionState: StateFlow<SessionState> = _sessionState

    init {
        checkSession()
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true)
            loginUseCase.login(LoginRequest(username, password)).collect { result ->
                when (result) {
                    is GeneralResult.Success -> {
                        Log.d("login", "login: ${result.data}")
                        result.data?.let { user ->
                            _authState.value = _authState.value.copy(user = user, isLoading = false)

                            sessionManager.login(user.toCurrentUser(), user.token)
                            _sessionState.value = _sessionState.value.copy(user = sessionManager.user, isLoading = false)
                        }
                    }
                    is GeneralResult.Error -> {
                        Log.d("login", "login: ${result.message}")
                        _authState.value = _authState.value.copy(error = result.message, isLoading = false)
                    }
                    GeneralResult.Loading -> {
                        Log.d("login", "login: Loading")
                        _authState.value = _authState.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    private fun checkSession() {
        if (sessionManager.isLoggedIn()) {
            _sessionState.value = _sessionState.value.copy(user = sessionManager.user, isLoading = false)
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.logout()
            _authState.value = _authState.value.copy(user = null, isLoading = false)
        }
    }
}
