package com.example.sonara.data.remote.datasource

import com.example.sonara.core.network.ApiResponse
import com.example.sonara.data.remote.api.SonaraApi
import com.example.sonara.data.remote.dto.response.EventoListDto
import com.example.sonara.data.remote.dto.response.UsuarioPerfilDto
import retrofit2.Response
import javax.inject.Inject

class EventoRemoteDataSource @Inject constructor(
    private val api: SonaraApi
) {
    suspend fun getEventos(): Response<ApiResponse<EventoListDto>> = api.getEventos()

    suspend fun getUsuarioById(id: Int): Response<ApiResponse<UsuarioPerfilDto>> =
        api.getUsuarioById(id)
}