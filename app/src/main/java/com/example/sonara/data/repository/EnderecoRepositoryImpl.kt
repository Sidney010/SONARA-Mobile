package com.example.sonara.data.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.core.network.safeApiCallSimple
import com.example.sonara.data.mapper.toDomain
import com.example.sonara.data.remote.datasource.EnderecoRemoteDataSource
import com.example.sonara.domain.model.Endereco
import com.example.sonara.domain.repository.EnderecoRepository
import javax.inject.Inject

class EnderecoRepositoryImpl @Inject constructor(

    private val remoteDataSource: EnderecoRemoteDataSource

) : EnderecoRepository {

    override suspend fun buscarCep(
        cep: String
    ): AppResult<Endereco> {

        return safeApiCallSimple(

            apiCall = {
                remoteDataSource.buscarCep(cep)
            },

            mapper = {
                it.toDomain()
            }
        )
    }
}