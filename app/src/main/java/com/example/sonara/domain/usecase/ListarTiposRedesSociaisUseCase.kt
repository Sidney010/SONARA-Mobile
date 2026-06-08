package com.example.sonara.domain.usecase

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.TipoRedeSocial
import com.example.sonara.domain.repository.RedeSocialRepository
import javax.inject.Inject

class ListarTiposRedesSociaisUseCase @Inject constructor(
    private val repository: RedeSocialRepository
) {
    suspend operator fun invoke(): AppResult<List<TipoRedeSocial>> =
        repository.getTiposRedesSociais()
}
