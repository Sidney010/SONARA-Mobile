package com.example.sonara.data.mapper

import com.example.sonara.data.remote.dto.request.EventoArtistaRequestDto
import com.example.sonara.data.remote.dto.response.evento.EventoArtistaDto
import com.example.sonara.domain.model.EventoArtista

import com.example.sonara.data.remote.dto.request.CandidaturaCreateRequestDto
import com.example.sonara.data.remote.dto.response.candidatura.CandidaturaDto

fun EventoArtistaDto.toDomain() = EventoArtista(
    idEventoArtista = idEventoArtista,
    artistaId = artistaId,
    eventoId = eventoId,
    cacheEsperado = cacheEsperado?.toDoubleOrNull(),
    cacheOfertado = cacheOfertado?.toDoubleOrNull(),
    cacheFinal = cacheFinal?.toDoubleOrNull(),
    contraProposta = contraProposta?.toDoubleOrNull(),
    sobreArtista = sobreArtista,
    motivoInscricao = motivoInscricao,
    status = "Pendente" // EventoArtistaDto não parece ter o campo status no JSON antigo
)

fun CandidaturaDto.toDomain() = EventoArtista(
    idEventoArtista = id_evento_artista,
    artistaId = artista_id ?: 0,
    eventoId = evento_id ?: 0,
    cacheEsperado = cache_esperado,
    cacheOfertado = cache_ofertado,
    cacheFinal = cache_final,
    contraProposta = contra_proposta?.toDoubleOrNull(),
    sobreArtista = sobre_artista,
    motivoInscricao = motivo_inscricao,
    status = status ?: "Pendente"
)

fun EventoArtista.toCandidaturaRequestDto() = CandidaturaCreateRequestDto(
    artista_id = artistaId,
    evento_id = eventoId,
    cache_ofertado = cacheOfertado,
    cache_esperado = cacheEsperado,
    sobre_artista = sobreArtista,
    motivo_inscricao = motivoInscricao
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