package com.example.sonara.domain.usecase

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.RedeSocial
import com.example.sonara.domain.repository.RedeSocialRepository
import javax.inject.Inject

class CreateRedeSocialUseCase @Inject constructor(
    private val repository: RedeSocialRepository
) {
    suspend operator fun invoke(redeSocial: RedeSocial): AppResult<RedeSocial> =
        repository.createRedeSocial(redeSocial)
}
