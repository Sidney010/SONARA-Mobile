package com.example.sonara.domain.usecase

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.UsuarioPerfil
import com.example.sonara.domain.repository.EventoRepository
import javax.inject.Inject

class BuscarUsuarioPorIdUseCase @Inject constructor(
    private val repository: EventoRepository
) {
    suspend operator fun invoke(id: Int): AppResult<UsuarioPerfil> =
        repository.buscarUsuarioPorId(id)
}
