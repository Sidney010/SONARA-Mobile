package com.example.sonara.data.remote.datasource

import com.example.sonara.data.remote.api.SonaraApi
import com.example.sonara.data.remote.dto.request.CreateUsuarioRequestDto
import retrofit2.Response
import javax.inject.Inject

class UsuarioRemoteDataSource @Inject constructor(
    private val api: SonaraApi
) {

    suspend fun registrarUsuario(
        request: CreateUsuarioRequestDto
    ): Response<Unit> {
        return api.registrarUsuario(request)
    }
}