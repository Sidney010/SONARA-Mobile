package com.example.sonara.data.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.core.network.safeApiCall
import com.example.sonara.data.mapper.toDomain
import com.example.sonara.data.remote.datasource.EventoRemoteDataSource
import com.example.sonara.domain.model.Evento
import com.example.sonara.domain.model.usuarioperfil.UsuarioPerfil
import com.example.sonara.domain.repository.EventoRepository
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
            mapper  = { dto -> dto.toDomain() }
        )
    }
}