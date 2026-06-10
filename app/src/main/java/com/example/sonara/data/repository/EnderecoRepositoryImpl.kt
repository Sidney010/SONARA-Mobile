package com.example.sonara.data.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.core.network.safeApiCallSimple
import com.example.sonara.data.mapper.toDomain
import com.example.sonara.data.remote.datasource.EnderecoRemoteDataSource
import com.example.sonara.domain.model.Endereco
import com.example.sonara.domain.repository.EnderecoRepository
import com.example.sonara.BuildConfig
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

    override suspend fun buscarCoordenadas(
        endereco: String
    ): AppResult<Pair<Double, Double>> {
        return try {
            val response = remoteDataSource.buscarCoordenadas(
                endereco = endereco,
                apiKey = BuildConfig.GEOAPIFY_API_KEY
            )
            val result = response.results.firstOrNull()
            if (result != null) {
                AppResult.Success(Pair(result.lat, result.lon))
            } else {
                AppResult.Error(Exception("Nenhum resultado encontrado"))
            }
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }
}