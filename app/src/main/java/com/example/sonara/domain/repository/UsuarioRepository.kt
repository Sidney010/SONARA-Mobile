package com.example.sonara.domain.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.Usuario

interface UsuarioRepository {

    suspend fun register(
        user: Usuario
    ): AppResult<Usuario>
}