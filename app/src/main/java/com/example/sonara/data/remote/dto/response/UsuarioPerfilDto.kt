package com.example.sonara.data.remote.dto.response

import com.google.gson.annotations.SerializedName

/** Perfil completo do usuário (GET /usuario/{id}) */
data class UsuarioPerfilDto(
    @SerializedName("id_usuario")         val id_usuario: Int,
    @SerializedName("nome")               val nome: String,
    @SerializedName("email")              val email: String,
    @SerializedName("cpf")                val cpf: String?,
    @SerializedName("data_nasc")          val data_nasc: String?,
    @SerializedName("telefone")           val telefone: String?,
    @SerializedName("nacionalidade_id")   val nacionalidade_id: Int?,
    @SerializedName("genero_id")          val genero_id: Int?,
    @SerializedName("tipo_usuario")       val tipo_usuario: String?,
    @SerializedName("nome_artistico")     val nome_artistico: String?,
    @SerializedName("descricao")          val descricao: String?,
    @SerializedName("criado")             val criado: String?,
    @SerializedName("ultima_atualizacao") val ultima_atualizacao: String?,
    @SerializedName("fotos")              val fotos: List<FotoDto>? = null
)