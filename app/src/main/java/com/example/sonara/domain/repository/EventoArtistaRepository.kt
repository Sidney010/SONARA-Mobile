package com.example.sonara.domain.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.EventoArtista

interface EventoArtistaRepository {
    suspend fun buscarPorId(id: Int): AppResult<EventoArtista>
    suspend fun criar(eventoArtista: EventoArtista): AppResult<EventoArtista>
    suspend fun atualizar(id: Int, eventoArtista: EventoArtista): AppResult<EventoArtista>
    suspend fun deletar(id: Int): AppResult<Unit>
    suspend fun aceitarConvite(id: Int): AppResult<EventoArtista>
    suspend fun recusarConvite(id: Int): AppResult<EventoArtista>
}