package com.yusuf.yusuf_mucahit_solmaz_final.data.datastore


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.model.CurrentUser
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.auth.LoginResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException


class SessionManager private constructor(@ApplicationContext private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("session_prefs")

    private val USERNAME_KEY = stringPreferencesKey("username")
    private val TOKEN_KEY = stringPreferencesKey("token")
    private val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
    private val USER_ID_KEY = stringPreferencesKey("user_id")
    private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    private val EMAIL_KEY = stringPreferencesKey("email")
    private val IMAGE_KEY = stringPreferencesKey("image")

    var user: CurrentUser? = null
        private set

    init {
        runBlocking { loadSession() }
    }

    val currentUserFlow: Flow<CurrentUser?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val isLoggedIn = preferences[IS_LOGGED_IN_KEY] ?: false
            if (isLoggedIn) {
                CurrentUser(
                    id = preferences[USER_ID_KEY]?.toInt() ?: 0,
                    username = preferences[USERNAME_KEY] ?: "",
                    token = preferences[TOKEN_KEY] ?: "",
                    refreshToken = preferences[REFRESH_TOKEN_KEY] ?: "",
                    email = preferences[EMAIL_KEY] ?: "",
                    image = preferences[IMAGE_KEY] ?: ""
                )
            } else {
                null
            }
        }


    private suspend fun loadSession() {
        val prefs = context.dataStore.data.first()
        val username = prefs[USERNAME_KEY]
        val token = prefs[TOKEN_KEY]
        val userId = prefs[USER_ID_KEY]
        val refreshToken = prefs[REFRESH_TOKEN_KEY]
        val isLoggedIn = prefs[IS_LOGGED_IN_KEY] ?: false
        val email = prefs[EMAIL_KEY]
        val image = prefs[IMAGE_KEY]

        if (isLoggedIn && username != null && token != null && userId != null && refreshToken != null) {
            user = CurrentUser(
                id = userId.toInt(),
                username = username,
                token = token,
                refreshToken = refreshToken,
                email = email ?: "",
                image = image ?: ""
            )
        }
    }

    suspend fun login(user: CurrentUser, token: String) {
        this.user = user
        context.dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = user.username
            prefs[TOKEN_KEY] = token
            prefs[USER_ID_KEY] = user.id.toString()
            prefs[REFRESH_TOKEN_KEY] = user.refreshToken
            prefs[IS_LOGGED_IN_KEY] = true
            prefs[EMAIL_KEY] = user.email
            prefs[IMAGE_KEY] = user.image
        }
    }

    suspend fun logout() {
        user = null
        context.dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = ""
            prefs[TOKEN_KEY] = ""
            prefs[USER_ID_KEY] = ""
            prefs[IS_LOGGED_IN_KEY] = false
            prefs[REFRESH_TOKEN_KEY] = ""
            prefs[EMAIL_KEY] = ""
            prefs[IMAGE_KEY] = ""
        }
    }

    fun isLoggedIn(): Boolean {
        return user != null
    }

    fun getToken(): String? {
        return user?.token
    }

    companion object {

        @Volatile
        private var INSTANCE: SessionManager? = null

        fun getInstance(context: Context): SessionManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SessionManager(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
}
