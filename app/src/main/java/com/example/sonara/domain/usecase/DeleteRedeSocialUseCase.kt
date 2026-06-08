package com.example.sonara.domain.usecase

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.repository.RedeSocialRepository
import javax.inject.Inject

class DeleteRedeSocialUseCase @Inject constructor(
    private val repository: RedeSocialRepository
) {
    suspend operator fun invoke(id: Int): AppResult<Unit> =
        repository.deleteRedeSocial(id)
}
