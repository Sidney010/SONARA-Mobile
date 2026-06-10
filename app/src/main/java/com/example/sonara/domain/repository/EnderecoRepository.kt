package com.example.sonara.domain.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.Endereco

interface EnderecoRepository {

    suspend fun buscarCep(
        cep: String
    ): AppResult<Endereco>

    suspend fun buscarCoordenadas(
        endereco: String
    ): AppResult<Pair<Double, Double>>
}