package com.example.sonara.domain.usecase

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.LoginResult
import com.example.sonara.domain.repository.UsuarioRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: UsuarioRepository
) {
    suspend operator fun invoke(email: String, senha: String): AppResult<LoginResult> {
        return repository.login(email, senha)
    }
}