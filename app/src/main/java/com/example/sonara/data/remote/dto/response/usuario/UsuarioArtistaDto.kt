package com.example.sonara.data.remote.dto.response.usuario

import com.example.sonara.data.remote.dto.response.usuario.perfil.UsuarioPerfilEventosDto
import com.google.gson.annotations.SerializedName

data class UsuarioArtistaDto (
    @SerializedName("id_artista")       val idAtista: Int?,
    @SerializedName("nome_artistico")   val nomeArtistico: String?,
    @SerializedName("descricao")        val descricao: String?,
    @SerializedName("generos_musicais") val generosMusicais: List<UsuarioArtistaGenerosMusicaisDto?>,
    @SerializedName("media_avaliacao")  val mediaAvaliacao: Double?,
    @SerializedName("total_avaliacoes") val totalAvaliacoes: Int?,
    @SerializedName("eventos")          val eventos: List<UsuarioPerfilEventosDto?>
)