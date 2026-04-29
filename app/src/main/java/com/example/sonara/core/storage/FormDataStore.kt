package com.example.sonara.core.storage

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "signup_form")

class FormDataStore(private val context: Context) {

    companion object {
        val NAME = stringPreferencesKey("name")
        val EMAIL = stringPreferencesKey("email")
        val CPF = stringPreferencesKey("cpf")
        val PASSWORD = stringPreferencesKey("password")
        val IMAGE = stringPreferencesKey("image")
        val USER_TYPE = stringPreferencesKey("user_type")
    }

    suspend fun saveForm(
        name: String,
        email: String,
        cpf: String,
        password: String,
        image: String?,
        userType: String
    ) {
        context.dataStore.edit {
            it[NAME] = name
            it[EMAIL] = email
            it[CPF] = cpf
            it[PASSWORD] = password
            image?.let { uri -> it[IMAGE] = uri }
            it[USER_TYPE] = userType
        }
    }

    fun getForm(): Flow<FormData> {
        return context.dataStore.data.map {
            FormData(
                name = it[NAME] ?: "",
                email = it[EMAIL] ?: "",
                cpf = it[CPF] ?: "",
                password = it[PASSWORD] ?: "",
                image = it[IMAGE],
                userType = it[USER_TYPE] ?: ""
            )
        }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}

data class FormData(
    val name: String,
    val email: String,
    val cpf: String,
    val password: String,
    val image: String?,
    val userType: String
)