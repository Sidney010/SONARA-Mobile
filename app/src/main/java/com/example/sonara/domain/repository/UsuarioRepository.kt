package com.example.sonara.domain.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.data.remote.dto.UsuarioRequest

interface UsuarioRepository {
    suspend fun register(user: UsuarioRequest): AppResult<Unit>
}