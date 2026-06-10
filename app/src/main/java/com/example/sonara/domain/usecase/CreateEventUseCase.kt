package com.example.sonara.domain.usecase

import com.example.sonara.core.common.AppResult
import com.example.sonara.data.remote.dto.request.EventoCreateRequestDto
import com.example.sonara.domain.repository.EventoRepository
import javax.inject.Inject

class CreateEventUseCase @Inject constructor(
    private val repository: EventoRepository
) {
    suspend operator fun invoke(request: EventoCreateRequestDto): AppResult<Int> {
        return repository.criarEvento(request)
    }
}