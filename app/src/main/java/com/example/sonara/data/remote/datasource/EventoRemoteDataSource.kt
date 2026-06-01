package com.example.sonara.data.remote.datasource

import com.example.sonara.core.network.ApiResponse
import com.example.sonara.data.remote.api.SonaraApi
import com.example.sonara.data.remote.dto.request.EventoCreateRequestDto
import com.example.sonara.data.remote.dto.response.evento.EventoDto
import com.example.sonara.data.remote.dto.response.evento.EventoListDto
import com.example.sonara.data.remote.dto.response.evento.EventoSimpleDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class EventoRemoteDataSource @Inject constructor(
    private val api: SonaraApi
) {
    suspend fun getEventos(): Response<ApiResponse<EventoListDto>> = api.getEventos()

    suspend fun getEventoById(id: Int): Response<ApiResponse<EventoDto>> = api.getEventoById(id)

    suspend fun getEventosPorOrganizador(organizadorId: Int): Response<ApiResponse<EventoListDto>> =
        api.getEventosPorOrganizador(organizadorId)

    suspend fun criarEvento(request: EventoCreateRequestDto): Response<ApiResponse<EventoSimpleDto>> =
        api.criarEvento(request)

    suspend fun editarEvento(id: Int, request: EventoCreateRequestDto): Response<ApiResponse<EventoSimpleDto>> =
        api.editarEvento(id, request)

    suspend fun deletarEvento(id: Int): Response<ApiResponse<Unit>> = api.deletarEvento(id)

    suspend fun uploadFotoEvento(foto: MultipartBody.Part, eventoId: RequestBody) =
        api.uploadFotoEvento(foto, eventoId)
}