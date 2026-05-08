package com.example.sonara.domain.usecase

import com.example.sonara.data.remote.dto.UsuarioRequest
import com.example.sonara.domain.repository.UsuarioRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: UsuarioRepository
) {
    suspend operator fun invoke(user: UsuarioRequest) = repository.register(user)
}