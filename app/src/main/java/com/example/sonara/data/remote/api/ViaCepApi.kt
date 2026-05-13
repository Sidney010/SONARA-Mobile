package com.example.sonara.data.remote.api

import com.example.sonara.data.remote.dto.response.ViaCepResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepApi {

    @GET("{cep}/json/")
    suspend fun buscarCep(
        @Path("cep") cep: String
    ): Response<ViaCepResponseDto>
}