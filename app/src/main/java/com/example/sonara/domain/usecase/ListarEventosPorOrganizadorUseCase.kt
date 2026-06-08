package com.example.sonara.domain.usecase

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.Evento
import com.example.sonara.domain.repository.EventoRepository
import javax.inject.Inject

class ListarEventosPorOrganizadorUseCase @Inject constructor(
    private val repository: EventoRepository
) {
    suspend operator fun invoke(organizadorId: Int): AppResult<List<Evento>> {
        return repository.listarEventosPorOrganizador(organizadorId)
    }
}
