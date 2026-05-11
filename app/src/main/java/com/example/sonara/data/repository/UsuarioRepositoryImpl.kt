package com.example.sonara.data.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.data.remote.mapper.toRequestDto
import com.example.sonara.data.remote.datasource.UsuarioRemoteDataSource
import com.example.sonara.domain.model.Usuario
import com.example.sonara.domain.repository.UsuarioRepository
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val remoteDataSource: UsuarioRemoteDataSource
) : UsuarioRepository {

    override suspend fun register(
        user: Usuario
    ): AppResult<Unit> {

        return try {

            val response = remoteDataSource
                .registrarUsuario(user.toRequestDto())

            if (response.isSuccessful) {
                AppResult.Success(Unit)
            } else {
                AppResult.Error(
                    Exception("Erro: ${response.code()}")
                )
            }

        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }
}