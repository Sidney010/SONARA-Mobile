package com.example.sonara.domain.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.Evento
import com.example.sonara.domain.model.UsuarioPerfil

interface EventoRepository {
    suspend fun listarEventos(): AppResult<List<Evento>>
    suspend fun buscarUsuarioPorId(id: Int): AppResult<UsuarioPerfil>
}