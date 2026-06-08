package com.example.sonara.data.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.core.network.safeApiCall
import com.example.sonara.data.remote.api.SonaraApi
import com.example.sonara.data.remote.dto.request.RedeSocialRequestDto
import com.example.sonara.domain.model.RedeSocial
import com.example.sonara.domain.model.TipoRedeSocial
import com.example.sonara.domain.repository.RedeSocialRepository
import javax.inject.Inject

class RedeSocialRepositoryImpl @Inject constructor(
    private val api: SonaraApi
) : RedeSocialRepository {

    override suspend fun getTiposRedesSociais(): AppResult<List<TipoRedeSocial>> =
        safeApiCall(
            apiCall = { api.getTiposRedesSociais() },
            mapper  = { dto ->
                dto.tipoRedesSociais.map {
                    TipoRedeSocial(it.id_tipo_redes_sociais, it.nome)
                }
            }
        )

    override suspend fun createRedeSocial(redeSocial: RedeSocial): AppResult<RedeSocial> =
        safeApiCall(
            apiCall = {
                api.createRedeSocial(
                    RedeSocialRequestDto(
                        link = redeSocial.link,
                        tipo_id = redeSocial.tipoId,
                        usuario_id = redeSocial.usuarioId ?: 0
                    )
                )
            },
            mapper = { dto ->
                RedeSocial(
                    id = dto.idRedesSociais,
                    link = dto.link,
                    tipoId = dto.tipoId,
                    usuarioId = dto.usuarioId
                )
            }
        )

    override suspend fun updateRedeSocial(id: Int, redeSocial: RedeSocial): AppResult<RedeSocial> =
        safeApiCall(
            apiCall = {
                api.updateRedeSocial(
                    id = id,
                    request = RedeSocialRequestDto(
                        link = redeSocial.link,
                        tipo_id = redeSocial.tipoId,
                        usuario_id = redeSocial.usuarioId ?: 0
                    )
                )
            },
            mapper = { dto ->
                RedeSocial(
                    id = dto.idRedesSociais,
                    link = dto.link,
                    tipoId = dto.tipoId,
                    usuarioId = dto.usuarioId
                )
            }
        )

    override suspend fun deleteRedeSocial(id: Int): AppResult<Unit> =
        safeApiCall(
            apiCall = { api.deleteRedeSocial(id) },
            mapper = { }
        )
}
