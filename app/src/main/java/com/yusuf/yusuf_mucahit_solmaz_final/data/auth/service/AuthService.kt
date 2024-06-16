package com.yusuf.yusuf_mucahit_solmaz_final.data.auth.service

import com.google.firebase.auth.FirebaseUser
import com.yusuf.yusuf_mucahit_solmaz_final.core.AppResult.GeneralResult
import kotlinx.coroutines.flow.Flow

interface AuthService {
    suspend fun signInWithEmailAndPassword(email: String, password: String): Flow<GeneralResult<FirebaseUser>>
    suspend fun signUpWithEmailAndPassword(email: String, password: String): Flow<GeneralResult<FirebaseUser>>
    fun signOut(): Flow<GeneralResult<Boolean>>
    fun isLoggedIn(): Flow<GeneralResult<Boolean>>
    fun sendPasswordResetEmail(email: String): Flow<GeneralResult<Boolean>>
}