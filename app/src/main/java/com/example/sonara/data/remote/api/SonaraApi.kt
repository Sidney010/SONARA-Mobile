package com.example.sonara.data.remote.api

import com.example.sonara.core.network.ApiResponse
import com.example.sonara.data.remote.dto.request.CreateUsuarioRequestDto
import com.example.sonara.data.remote.dto.response.UsuarioResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SonaraApi {

    @POST("usuarios")
    suspend fun register(

        @Body
        request: CreateUsuarioRequestDto

    ): Response<ApiResponse<UsuarioResponseDto>>
}