package com.yusuf.yusuf_mucahit_solmaz_final.data.auth.implementation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.google.firebase.auth.FirebaseUser
import com.yusuf.yusuf_mucahit_solmaz_final.core.AppResult.GeneralResult
import com.yusuf.yusuf_mucahit_solmaz_final.data.auth.service.AuthService

class AuthServiceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,

) : AuthService {

    private val auth = getInstance()
    override suspend fun signInWithEmailAndPassword(email: String, password: String): Flow<GeneralResult<FirebaseUser>> =
        flow {
            emit(GeneralResult.Loading)
            try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                emit(GeneralResult.Success(result.user))
            } catch (e: Exception) {
                emit(GeneralResult.Error(e.message ?: "Something went wrong"))
            }
        }.flowOn(Dispatchers.IO)


    override suspend fun signUpWithEmailAndPassword(email: String, password: String): Flow<GeneralResult<FirebaseUser>> = flow {
        emit(GeneralResult.Loading)
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user

            emit(GeneralResult.Success(user))
        } catch (e: Exception) {
            emit(GeneralResult.Error(e.message ?: "Something went wrong"))
        }
    }.flowOn(Dispatchers.IO)


    override fun signOut(): Flow<GeneralResult<Boolean>> = flow {
        emit(GeneralResult.Loading)
        try {
            firebaseAuth.signOut()
            emit(GeneralResult.Success(true))
        } catch (e: Exception) {
            emit(GeneralResult.Error(e.message ?: "Something went wrong"))
        }
    }.flowOn(Dispatchers.IO)


    override fun isLoggedIn(): Flow<GeneralResult<Boolean>> = flow {
        emit(GeneralResult.Loading)
        try {
            val user = firebaseAuth.currentUser
            if (user != null) {
                emit(GeneralResult.Success(true))}
            else {
                emit(GeneralResult.Success(false))
            }
        }
        catch (e:Exception){
            emit(GeneralResult.Error(e.message ?: "Something went wrong"))
        }
    }

    override fun sendPasswordResetEmail(email: String): Flow<GeneralResult<Boolean>> = flow {
        emit(GeneralResult.Loading)
        try {

            auth.sendPasswordResetEmail(email).await()
            emit(GeneralResult.Success(true))
        }
        catch (e: Exception){
            emit(GeneralResult.Error(e.message ?: "Something went wrong"))
        }
    }
}