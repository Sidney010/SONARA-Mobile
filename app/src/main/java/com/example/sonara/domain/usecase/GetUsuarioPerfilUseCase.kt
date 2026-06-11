package com.example.sonara.domain.usecase

import com.example.sonara.core.common.AppResult
import com.example.sonara.core.network.safeApiCall
import com.example.sonara.data.remote.api.SonaraApi
import com.example.sonara.data.remote.dto.response.usuario.perfil.UsuarioPerfilDto
import javax.inject.Inject

class GetUsuarioPerfilUseCase @Inject constructor(
    private val api: SonaraApi
) {
    suspend operator fun invoke(userId: Int): AppResult<UsuarioPerfilDto> {
        return safeApiCall(
            apiCall = { api.getUsuarioById(userId) },
            mapper = { it.usuario }
        )
    }
}
