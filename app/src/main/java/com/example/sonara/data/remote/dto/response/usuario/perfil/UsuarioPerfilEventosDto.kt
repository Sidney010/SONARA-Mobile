package com.example.sonara.data.remote.dto.response.usuario.perfil

import com.google.gson.annotations.SerializedName

data class UsuarioPerfilEventosDto(
    @SerializedName("id_evento")          val idEvento: Int,
    @SerializedName("data")               val eventoData: String?,
    @SerializedName("nome")               val eventoNome: String?,
    @SerializedName("cache")              val cache: UsuarioPerfilEventosCacheDto?,
    @SerializedName("status")             val status: String?,
    @SerializedName("fotos")              val fotos: List<UsuarioPerfilEventoFotosDto?> = emptyList(),
    @SerializedName("endereco")           val endereco: UsuarioPerfilEventosEnderecoDto?,
    @SerializedName("hora_fim")           val horaFim: String?,
    @SerializedName("descricao")          val descricao: String?,
    @SerializedName("hora_inicio")        val horaInicio: String?,
    @SerializedName("sobre_artista")      val sobreArtista: String?,
    @SerializedName("motivo_inscricao")   val motivoInscricao: String?,
    @SerializedName("id_evento_artista")  val idEventoArtista: Int



)
