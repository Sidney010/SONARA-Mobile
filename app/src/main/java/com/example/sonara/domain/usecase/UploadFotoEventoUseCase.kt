package com.example.sonara.domain.usecase

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.repository.EventoRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadFotoEventoUseCase @Inject constructor(
    private val repository: EventoRepository
) {
    suspend operator fun invoke(eventoId: Int, fotoPart: MultipartBody.Part): AppResult<String> {
        return repository.uploadFotoEvento(eventoId, fotoPart)
    }
}