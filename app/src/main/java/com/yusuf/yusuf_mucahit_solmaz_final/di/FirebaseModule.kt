package com.yusuf.yusuf_mucahit_solmaz_final.di

import com.google.firebase.auth.FirebaseAuth
import com.yusuf.yusuf_mucahit_solmaz_final.data.auth.implementation.AuthServiceImpl
import com.yusuf.yusuf_mucahit_solmaz_final.data.auth.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthService(firebaseAuth: FirebaseAuth): AuthService {
        return AuthServiceImpl(firebaseAuth)
    }

}