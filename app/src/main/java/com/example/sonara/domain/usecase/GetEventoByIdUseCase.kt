package com.example.sonara.domain.usecase

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.Evento
import com.example.sonara.domain.repository.EventoRepository
import javax.inject.Inject

class GetEventoByIdUseCase @Inject constructor(
    private val repository: EventoRepository
) {
    suspend operator fun invoke(id: Int): AppResult<Evento> = repository.buscarEventoPorId(id)
}