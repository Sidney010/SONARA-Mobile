package com.example.sonara.data.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.core.network.safeApiCall
import com.example.sonara.data.mapper.toDomain
import com.example.sonara.data.mapper.toRequestDto
import com.example.sonara.data.remote.datasource.UsuarioRemoteDataSource
import com.example.sonara.domain.model.Usuario
import com.example.sonara.domain.repository.UsuarioRepository
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val remoteDataSource: UsuarioRemoteDataSource
) : UsuarioRepository {

    override suspend fun register(
        user: Usuario
    ): AppResult<Usuario> {

        return safeApiCall(

            apiCall = {
                remoteDataSource.register(
                    user.toRequestDto()
                )
            },

            mapper = { response ->
                response.toDomain()
            }
        )
    }
}