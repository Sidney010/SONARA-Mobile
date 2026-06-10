package com.example.sonara.data.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.core.network.safeApiCall
import com.example.sonara.data.mapper.toDomain
import com.example.sonara.data.remote.datasource.EventoRemoteDataSource
import com.example.sonara.data.remote.api.SonaraApi
import com.example.sonara.data.remote.dto.request.EventoCreateRequestDto
import com.example.sonara.domain.model.Evento
import com.example.sonara.domain.model.usuarioperfil.UsuarioPerfil
import com.example.sonara.domain.repository.EventoRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class EventoRepositoryImpl @Inject constructor(
    private val remoteDataSource: EventoRemoteDataSource
) : EventoRepository {

    override suspend fun listarEventos(): AppResult<List<Evento>> {
        return safeApiCall(
            apiCall = { remoteDataSource.getEventos() },
            mapper  = { dto -> dto.eventos.map { it.toDomain() } }
        )
    }

    override suspend fun buscarEventoPorId(id: Int): AppResult<Evento> {
        return safeApiCall(
            apiCall = { remoteDataSource.getEventoById(id) },
            mapper  = { dto -> dto.evento.toDomain() }
        )
    }

    override suspend fun listarEventosPorOrganizador(organizadorId: Int): AppResult<List<Evento>> {
        return safeApiCall(
            apiCall = { remoteDataSource.getEventosPorOrganizador(organizadorId) },
            mapper  = { dto -> dto.eventos.map { it.toDomain() } }
        )
    }

    override suspend fun criarEvento(evento: EventoCreateRequestDto): AppResult<Int> {
        return safeApiCall(
            apiCall = { remoteDataSource.criarEvento(evento) },
            mapper  = { dto -> dto.id_evento }
        )
    }

    override suspend fun uploadFotoEvento(eventoId: Int, fotoPart: MultipartBody.Part): AppResult<String> {
        val eventoIdBody = eventoId.toString().toRequestBody(MultipartBody.FORM)
        return safeApiCall(
            apiCall = { remoteDataSource.uploadFotoEvento(fotoPart, eventoIdBody) },
            mapper  = { dto -> dto.foto ?: "" }
        )
    }
}