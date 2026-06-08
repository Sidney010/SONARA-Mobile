package com.example.sonara.data.mapper

import com.example.sonara.data.remote.dto.request.EventoArtistaRequestDto
import com.example.sonara.data.remote.dto.response.evento.EventoArtistaDto
import com.example.sonara.domain.model.EventoArtista

fun EventoArtistaDto.toDomain() = EventoArtista(
    idEventoArtista = idEventoArtista,
    artistaId = artistaId,
    eventoId = eventoId,
    cacheEsperado = cacheEsperado?.toDoubleOrNull(),
    cacheOfertado = cacheOfertado?.toDoubleOrNull(),
    cacheFinal = cacheFinal?.toDoubleOrNull(),
    contraProposta = contraProposta?.toDoubleOrNull(),
    sobreArtista = sobreArtista,
    motivoInscricao = motivoInscricao
)

fun EventoArtista.toRequestDto() = EventoArtistaRequestDto(
    artistaId = artistaId,
    eventoId = eventoId,
    cacheEsperado = cacheEsperado,
    cacheOfertado = cacheOfertado,
    cacheFinal = cacheFinal,
    contraProposta = contraProposta,
    sobreArtista = sobreArtista,
    motivoInscricao = motivoInscricao
)