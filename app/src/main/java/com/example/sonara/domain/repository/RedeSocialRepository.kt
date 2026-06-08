package com.example.sonara.domain.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.RedeSocial
import com.example.sonara.domain.model.TipoRedeSocial

interface RedeSocialRepository {
    suspend fun getTiposRedesSociais(): AppResult<List<TipoRedeSocial>>
    suspend fun createRedeSocial(redeSocial: RedeSocial): AppResult<RedeSocial>
    suspend fun updateRedeSocial(id: Int, redeSocial: RedeSocial): AppResult<RedeSocial>
    suspend fun deleteRedeSocial(id: Int): AppResult<Unit>
}
