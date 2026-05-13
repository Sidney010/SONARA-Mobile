package com.example.sonara.data.remote.datasource

import com.example.sonara.data.remote.api.ViaCepApi
import com.example.sonara.data.remote.dto.response.ViaCepResponseDto
import retrofit2.Response
import javax.inject.Inject

class EnderecoRemoteDataSource @Inject constructor(

    private val api: ViaCepApi

) {

    suspend fun buscarCep(
        cep: String
    ): Response<ViaCepResponseDto> {

        return api.buscarCep(cep)
    }
}