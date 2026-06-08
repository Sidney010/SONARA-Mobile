package com.example.sonara.core.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.authDataStore by preferencesDataStore(name = "auth_prefs")

@Singleton
class TokenManager @Inject constructor(
    private val context: Context
) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val USER_TYPE_KEY = stringPreferencesKey("user_type")
        private val USER_PHOTO_KEY = stringPreferencesKey("user_photo")
        private val ARTIST_ID_KEY = stringPreferencesKey("artist_id")
        private val ORGANIZER_ID_KEY = stringPreferencesKey("organizer_id")
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

    // NOVO: tipo do usuário ("Artista", "Organizador", "Usuário", "Anônimo")
    val userType: Flow<String?> = context.authDataStore.data.map {
        it[USER_TYPE_KEY]?.takeIf { t -> t.isNotBlank() }
    }

    val userPhoto: Flow<String?> = context.authDataStore.data.map {
        it[USER_PHOTO_KEY]?.takeIf { p -> p.isNotBlank() }
    }

    val organizerId: Flow<String?> = context.authDataStore.data.map {
        it[ORGANIZER_ID_KEY]?.takeIf { id -> id.isNotBlank() }
    }

    val artistId: Flow<String?> = context.authDataStore.data.map {
        it[ARTIST_ID_KEY]?.takeIf { id -> id.isNotBlank() }
    }

    /**
     * Salva sessão autenticada (após login ou cadastro bem-sucedido).
     * [userType] não vem na resposta de login da API, use o valor salvo no cadastro
     * ou passe "Usuário" como padrão.
     */
    suspend fun saveSession(
        token: String,
        userId: Int,
        userName: String,
        userType: String,
        photoUrl: String?,
        artistId: Int?,
        organizerId: Int?
    ) {
        context.authDataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[USER_ID_KEY] = userId.toString()
            prefs[USER_NAME_KEY] = userName
            prefs[USER_TYPE_KEY] = userType
            prefs[USER_PHOTO_KEY] = photoUrl ?: ""
            prefs[ARTIST_ID_KEY] = artistId?.toString() ?: ""
            prefs[ORGANIZER_ID_KEY] = organizerId?.toString() ?: ""
        }
    }

    /**
     * Salva sessão anônima — sem token, mas com nome e tipo definidos
     * para o header exibir "Anônimo / Usuário".
     */
    suspend fun saveAnonymousSession() {
        context.authDataStore.edit {
            it.remove(TOKEN_KEY)
            it.remove(USER_ID_KEY)
            it[USER_NAME_KEY] = "Anônimo"
            it[USER_TYPE_KEY] = "Usuário"
            it[USER_PHOTO_KEY]?.takeIf { p -> p.isNotBlank() }
        }
    }

    suspend fun clearSession() {
        context.authDataStore.edit { it.clear() }
    }

    suspend fun getToken(): String? = token.first()

    suspend fun getUserId(): Int? = userId.first()?.toIntOrNull()

    suspend fun isLoggedIn(): Boolean = !getToken().isNullOrBlank()
}