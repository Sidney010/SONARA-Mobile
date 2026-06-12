package com.example.sonara.data.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.core.network.safeApiCall
import com.example.sonara.core.network.safeApiCallSimple
import com.example.sonara.data.mapper.toDomain
import com.example.sonara.data.mapper.toCandidaturaRequestDto
import com.example.sonara.data.mapper.toRequestDto
import com.example.sonara.data.remote.api.SonaraApi
import com.example.sonara.domain.model.EventoArtista
import com.example.sonara.domain.repository.EventoArtistaRepository
import javax.inject.Inject

class EventoArtistaRepositoryImpl @Inject constructor(
    private val api: SonaraApi
) : EventoArtistaRepository {

    override suspend fun buscarPorId(id: Int): AppResult<EventoArtista> {
        return safeApiCallSimple(
            apiCall = { api.getEventoArtistaById(id) },
            mapper = { it.response.eventoArtista.toDomain() }
        )
    }

    override suspend fun criar(eventoArtista: EventoArtista): AppResult<EventoArtista> {
        return safeApiCall(
            apiCall = { api.criarCandidatura(eventoArtista.toCandidaturaRequestDto()) },
            mapper = { it.toDomain() }
        )
    }

    override suspend fun atualizar(id: Int, eventoArtista: EventoArtista): AppResult<EventoArtista> {
        return safeApiCallSimple(
            apiCall = { api.atualizarEventoArtista(id, eventoArtista.toRequestDto()) },
            mapper = { it.response.eventoArtista.toDomain() }
        )
    }

    override suspend fun deletar(id: Int): AppResult<Unit> {
        return try {
            val response = api.deletarEventoArtista(id)
            if (response.isSuccessful) {
                AppResult.Success(Unit)
            } else {
                AppResult.Error(Exception("Erro ao deletar candidatura"))
            }
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }

    override suspend fun aceitarConvite(id: Int): AppResult<EventoArtista> {
        return safeApiCall(
            apiCall = { api.aceitarConvite(id) },
            mapper = { it.toDomain() }
        )
    }

    override suspend fun recusarConvite(id: Int): AppResult<EventoArtista> {
        return safeApiCall(
            apiCall = { api.recusarConvite(id) },
            mapper = { it.toDomain() }
        )
    }
}
