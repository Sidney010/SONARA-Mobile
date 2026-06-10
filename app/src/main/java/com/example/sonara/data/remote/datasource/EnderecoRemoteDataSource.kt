package com.example.sonara.data.remote.datasource

import com.example.sonara.data.remote.api.ViaCepApi
import com.example.sonara.data.remote.api.geoapify.GeoapifyApiService
import com.example.sonara.data.remote.dto.response.ViaCepResponseDto
import com.example.sonara.data.remote.dto.response.geoapify.GeoapifyResponse
import retrofit2.Response
import javax.inject.Inject

class EnderecoRemoteDataSource @Inject constructor(

    private val api: ViaCepApi,
    private val geoapifyApi: GeoapifyApiService

) {

    suspend fun buscarCep(
        cep: String
    ): Response<ViaCepResponseDto> {

        return api.buscarCep(cep)
    }

    suspend fun buscarCoordenadas(
        endereco: String,
        apiKey: String
    ): GeoapifyResponse {
        return geoapifyApi.buscarCoordenadas(text = endereco, apiKey = apiKey)
    }
}