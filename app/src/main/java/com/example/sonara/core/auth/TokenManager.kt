package com.example.sonara.core.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Definição da extensão do DataStore de forma segura
private val Context.authDataStore by preferencesDataStore(name = "auth_prefs")

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("bearer_token")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
    }

    val token: Flow<String?> = context.authDataStore.data.map {
        it[TOKEN_KEY]?.takeIf { t -> t.isNotBlank() }
    }

    val userId: Flow<String?> = context.authDataStore.data.map {
        it[USER_ID_KEY]?.takeIf { id -> id.isNotBlank() }
    }

    val userName: Flow<String?> = context.authDataStore.data.map {
        it[USER_NAME_KEY]?.takeIf { n -> n.isNotBlank() }
    }

    suspend fun saveSession(token: String, userId: Int, userName: String) {
        context.authDataStore.edit {
            it[TOKEN_KEY] = token
            it[USER_ID_KEY] = userId.toString()
            it[USER_NAME_KEY] = userName
        }
    }

    suspend fun clearSession() {
        context.authDataStore.edit { it.clear() }
    }

    suspend fun getToken(): String? = token.first()

    suspend fun isLoggedIn(): Boolean = !getToken().isNullOrBlank()
}