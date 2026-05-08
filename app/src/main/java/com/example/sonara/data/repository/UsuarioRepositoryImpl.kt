package com.example.sonara.data.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.data.remote.SonaraApi
import com.example.sonara.data.remote.dto.UsuarioRequest
import com.example.sonara.domain.repository.UsuarioRepository
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val api: SonaraApi
) : UsuarioRepository {
    override suspend fun register(user: UsuarioRequest): AppResult<Unit> {
        return try {
            val response = api.registrarUsuario(user)
            if (response.isSuccessful) AppResult.Success(Unit)
            else AppResult.Error(Exception("Erro: ${response.code()}"))
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }
}