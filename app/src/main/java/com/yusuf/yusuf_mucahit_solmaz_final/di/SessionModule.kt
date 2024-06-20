package com.yusuf.yusuf_mucahit_solmaz_final.di

import android.content.Context
import com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.SessionManager
import com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.repo.UserSessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionModule {

    @Provides
    @Singleton
    fun provideUserRepository(sessionManager: SessionManager): UserSessionRepository {
        return UserSessionRepository(sessionManager)
    }

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager.getInstance(context)
    }
}