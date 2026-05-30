package com.example.sonara.domain.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.LoginResult
import com.example.sonara.domain.model.Usuario
import com.example.sonara.domain.model.usuarioperfil.UsuarioPerfil

interface UsuarioRepository {
    suspend fun register(user: Usuario, photoFilePath: String? = null): AppResult<Usuario>
    suspend fun login(email: String, senha: String): AppResult<LoginResult>

    suspend fun buscarUsuarioPorId(id: Int): AppResult<UsuarioPerfil>


}