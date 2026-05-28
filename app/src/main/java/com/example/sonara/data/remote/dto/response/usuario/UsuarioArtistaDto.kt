package com.example.sonara.data.remote.dto.response.usuario

import com.google.gson.annotations.SerializedName

data class UsuarioArtistaDto (
    @SerializedName("id_artista")       val id_artista: Int?,
    @SerializedName("nome_artistico")   val nome_artistico: String?,
    @SerializedName("descricao")        val descricao: String?,
    @SerializedName("generos_musicais") val generos_musicais: List<UsuarioArtistaGenerosMusicaisDto> = emptyList(),
    @SerializedName("media_avaliacao")  val media_avaliacao: Double?,
    @SerializedName("total_avaliacoes") val total_avaliacoes: Int?,
    @SerializedName("eventos")          val eventos:String?//chama o evento
)