package com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.repo

import com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.SessionManager
import com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.model.CurrentUser
import kotlinx.coroutines.flow.Flow

class UserSessionRepository(private val sessionManager: SessionManager) {

    val currentUser: Flow<CurrentUser?> = sessionManager.currentUserFlow

    suspend fun login(user: CurrentUser, token: String) {
        sessionManager.login(user, token)
    }

    suspend fun logout() {
        sessionManager.logout()
    }

    fun isLoggedIn(): Boolean {
        return sessionManager.isLoggedIn()
    }

    fun getUserId(): Int {
        return sessionManager.user?.id ?: 0
    }

    fun getUser(): CurrentUser?{
        return sessionManager.user
    }
}