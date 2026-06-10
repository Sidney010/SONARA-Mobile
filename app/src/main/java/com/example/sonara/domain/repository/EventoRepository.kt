package com.example.sonara.domain.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.data.remote.dto.request.EventoCreateRequestDto
import com.example.sonara.domain.model.Evento
import com.example.sonara.domain.model.usuarioperfil.UsuarioPerfil
import okhttp3.MultipartBody

interface EventoRepository {
    suspend fun listarEventos(): AppResult<List<Evento>>
    suspend fun buscarEventoPorId(id: Int): AppResult<Evento>
    suspend fun listarEventosPorOrganizador(organizadorId: Int): AppResult<List<Evento>>
    suspend fun criarEvento(evento: EventoCreateRequestDto): AppResult<Int>
    suspend fun uploadFotoEvento(eventoId: Int, fotoPart: MultipartBody.Part): AppResult<String>
}