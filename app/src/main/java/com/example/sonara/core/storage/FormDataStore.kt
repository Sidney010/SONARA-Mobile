package com.example.sonara.core.storage

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "signup_form"
)

class FormDataStore(
    private val context: Context
) {

    companion object {

        private val NAME = stringPreferencesKey("name")
        private val EMAIL = stringPreferencesKey("email")
        private val CPF = stringPreferencesKey("cpf")
        private val PASSWORD = stringPreferencesKey("password")
        private val IMAGE = stringPreferencesKey("image")
        private val USER_TYPE = stringPreferencesKey("user_type")

        // ENDEREÇO

        private val CEP = stringPreferencesKey("cep")
        private val RUA = stringPreferencesKey("rua")
        private val BAIRRO = stringPreferencesKey("bairro")
        private val CIDADE = stringPreferencesKey("cidade")
        private val UF = stringPreferencesKey("uf")
    }

    suspend fun saveForm(
        data: FormData
    ) {

        context.dataStore.edit {

            it[NAME] = data.name
            it[EMAIL] = data.email
            it[CPF] = data.cpf
            it[PASSWORD] = data.password
            it[IMAGE] = data.image ?: ""
            it[USER_TYPE] = data.userType

            // ENDEREÇO
            it[CEP] = data.cep
            it[RUA] = data.rua
            it[BAIRRO] = data.bairro
            it[CIDADE] = data.cidade
            it[UF] = data.uf
        }
    }

    fun getForm() = context.dataStore.data.map {

        FormData(

            name = it[NAME] ?: "",
            email = it[EMAIL] ?: "",
            cpf = it[CPF] ?: "",
            password = it[PASSWORD] ?: "",
            image = it[IMAGE],
            userType = it[USER_TYPE] ?: "",

            // ENDEREÇO
            cep = it[CEP] ?: "",
            rua = it[RUA] ?: "",
            bairro = it[BAIRRO] ?: "",
            cidade = it[CIDADE] ?: "",
            uf = it[UF] ?: ""
        )
    }
    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}

