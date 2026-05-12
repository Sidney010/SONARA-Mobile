package com.example.sonara.data.remote.datasource

import com.example.sonara.core.network.ApiResponse
import com.example.sonara.data.remote.api.SonaraApi
import com.example.sonara.data.remote.dto.request.CreateUsuarioRequestDto
import com.example.sonara.data.remote.dto.response.UsuarioResponseDto
import retrofit2.Response
import javax.inject.Inject

class UsuarioRemoteDataSource @Inject constructor(
    private val api: SonaraApi
) {

    suspend fun register(
        request: CreateUsuarioRequestDto
    ): Response<ApiResponse<UsuarioResponseDto>> {

        return api.register(request)
    }
}