package com.example.sonara.domain.usecase

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.Usuario
import com.example.sonara.domain.repository.UsuarioRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: UsuarioRepository
) {

    suspend operator fun invoke(
        user: Usuario
    ): AppResult<Usuario> {

        return repository.register(user)
    }
}