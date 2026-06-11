package com.example.sonara.domain.usecase

import com.example.sonara.core.common.AppResult
import com.example.sonara.data.remote.dto.response.usuario.ArtistaDto
import com.example.sonara.domain.repository.UsuarioRepository
import javax.inject.Inject

class GetArtistasUseCase @Inject constructor(
    private val repository: UsuarioRepository
) {
    suspend operator fun invoke(): AppResult<List<ArtistaDto>> {
        return repository.getArtistas()
    }
}
