package com.example.sonara.domain.usecase

import com.example.sonara.domain.model.Usuario
import com.example.sonara.domain.repository.UsuarioRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: UsuarioRepository
) {

    suspend operator fun invoke(
        usuario: Usuario
    ) = repository.register(usuario)
}