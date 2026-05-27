package com.example.sonara.data.remote.dto.response

import com.google.gson.annotations.SerializedName


data class UsuarioLoginDto(
    @SerializedName("id_usuario")       val id_usuario: Int,
    @SerializedName("nome")             val nome: String,
    @SerializedName("email")            val email: String,
    @SerializedName("cpf")              val cpf: String?,
    @SerializedName("data_nasc")        val data_nasc: String?,
    @SerializedName("telefone")         val telefone: String?,
    // Agora são strings, não IDs
    @SerializedName("generoId")           val generoId: Int?,
    @SerializedName("nacionalidadeId")    val nacionalidadeId: Int?,
    @SerializedName("foto_url")         val foto_url: String?,
    // Campos artísticos
    @SerializedName("nome_artistico")   val nome_artistico: String?,
    @SerializedName("descricao_artista")val descricao_artista: String?,
    @SerializedName("tipo_usuario")     val tipo_usuario: String?,
    // Endereço (disponível no login)
    @SerializedName("cep")              val cep: String?,
    @SerializedName("logradouro")       val logradouro: String?,
    @SerializedName("numero")           val numero: String?,
    @SerializedName("complemento")      val complemento: String?,
    @SerializedName("bairro")           val bairro: String?,
    @SerializedName("cidade")           val cidade: String?,
    @SerializedName("estado")           val estado: String?,
    // Redes sociais (opcionais)
    @SerializedName("rede_social_link") val rede_social_link: String?,
    @SerializedName("rede_social_tipo") val rede_social_tipo: String?
) {

}