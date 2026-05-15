package com.example.sonara.core.storage

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "signup_form")

class FormDataStore(private val context: Context) {

    companion object {
        // Dados pessoais
        private val NAME           = stringPreferencesKey("name")
        private val EMAIL          = stringPreferencesKey("email")
        private val CPF            = stringPreferencesKey("cpf")
        private val PASSWORD       = stringPreferencesKey("password")
        private val IMAGE          = stringPreferencesKey("image")
        private val DATA_NASC      = stringPreferencesKey("data_nasc")
        private val TELEFONE       = stringPreferencesKey("telefone")

        // Tipo de usuário
        private val USER_TYPE      = stringPreferencesKey("user_type")

        // Artísticos
        private val NOME_ARTISTICO = stringPreferencesKey("nome_artistico")
        private val DESCRICAO      = stringPreferencesKey("descricao")

        // Catálogos
        private val NACIONALIDADE_ID   = stringPreferencesKey("nacionalidade_id")
        private val GENERO_ID          = stringPreferencesKey("genero_id")
        private val GENEROS_MUSICAIS   = stringPreferencesKey("generos_musicais")

        // Endereço
        private val CEP         = stringPreferencesKey("cep")
        private val RUA         = stringPreferencesKey("rua")
        private val BAIRRO      = stringPreferencesKey("bairro")
        private val CIDADE      = stringPreferencesKey("cidade")
        private val UF          = stringPreferencesKey("uf")
        private val NUMERO      = stringPreferencesKey("numero")
        private val COMPLEMENTO = stringPreferencesKey("complemento")
    }

    suspend fun saveForm(data: FormData) {
        context.dataStore.edit {
            it[NAME]           = data.name
            it[EMAIL]          = data.email
            it[CPF]            = data.cpf
            it[PASSWORD]       = data.password
            it[IMAGE]          = data.image ?: ""
            it[DATA_NASC]      = data.dataNasc
            it[TELEFONE]       = data.telefone
            it[USER_TYPE]      = data.userType
            it[NOME_ARTISTICO] = data.nomeArtistico
            it[DESCRICAO]      = data.descricao
            it[NACIONALIDADE_ID]   = data.nacionalidadeId
            it[GENERO_ID]          = data.generoId
            it[GENEROS_MUSICAIS]   = data.generosMusicais
            it[CEP]         = data.cep
            it[RUA]         = data.rua
            it[BAIRRO]      = data.bairro
            it[CIDADE]      = data.cidade
            it[UF]          = data.uf
            it[NUMERO]      = data.numero
            it[COMPLEMENTO] = data.complemento
        }
    }

    fun getForm() = context.dataStore.data.map {
        FormData(
            name           = it[NAME]           ?: "",
            email          = it[EMAIL]          ?: "",
            cpf            = it[CPF]            ?: "",
            password       = it[PASSWORD]       ?: "",
            image          = it[IMAGE]?.takeIf { s -> s.isNotBlank() },
            dataNasc       = it[DATA_NASC]      ?: "",
            telefone       = it[TELEFONE]       ?: "",
            userType       = it[USER_TYPE]      ?: "",
            nomeArtistico  = it[NOME_ARTISTICO] ?: "",
            descricao      = it[DESCRICAO]      ?: "",
            nacionalidadeId= it[NACIONALIDADE_ID]   ?: "",
            generoId       = it[GENERO_ID]          ?: "",
            generosMusicais= it[GENEROS_MUSICAIS]   ?: "",
            cep            = it[CEP]         ?: "",
            rua            = it[RUA]         ?: "",
            bairro         = it[BAIRRO]      ?: "",
            cidade         = it[CIDADE]      ?: "",
            uf             = it[UF]          ?: "",
            numero         = it[NUMERO]      ?: "",
            complemento    = it[COMPLEMENTO] ?: ""
        )
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}