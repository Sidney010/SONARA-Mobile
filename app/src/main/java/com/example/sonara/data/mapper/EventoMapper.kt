package com.example.sonara.data.mapper

import com.example.sonara.data.remote.dto.response.evento.EventoDto
import com.example.sonara.data.remote.dto.response.usuario.perfil.UsuarioPerfilEventosDto
import com.example.sonara.domain.model.ArtistaResumo
import com.example.sonara.domain.model.Evento

fun EventoDto.toDomain() = Evento(
    id = idEvento,
    nome = nome,
    descricao = descricao,
    local = local,
    data = data,
    horaInicio = horaInicio,
    horaFim = horaFim,
    fotosUrls = fotos.mapNotNull { it.url },
    mediaAvaliacao = avaliacao?.media,
    totalAvaliacoes = avaliacao?.total ?: 0,
    logradouro = endereco?.logradouro,
    numero = endereco?.numero,
    bairro = endereco?.bairro,
    cidade = endereco?.cidade,
    estado = endereco?.estado,
    cep = endereco?.cep,
    complemento = endereco?.complemento,
    latitude = endereco?.latitude,
    longitude = endereco?.longitude,
    organizadorNome = organizador?.nome,
    organizadorEmail = organizador?.email,
    artista = artistas.firstOrNull()?.let {
        ArtistaResumo(
            id = it.artista?.idUsuario,
            nome = it.artista?.nomeArtistico,
            foto = it.artista?.foto,
            sobre = it.informacoes?.sobreArtista
        )
    }
)

fun UsuarioPerfilEventosDto.toEventoDomain() = Evento(
    id = idEvento,
    nome = eventoNome ?: "Sem nome",
    descricao = descricao,
    local = null,
    data = eventoData,
    horaInicio = horaInicio,
    horaFim = horaFim,
    fotosUrls = fotos?.mapNotNull { it?.url } ?: emptyList(),
    mediaAvaliacao = null,
    totalAvaliacoes = 0,
    logradouro = endereco?.logradouro,
    numero = endereco?.numero,
    bairro = endereco?.bairro,
    cidade = endereco?.cidade,
    estado = endereco?.estado,
    cep = endereco?.cep,
    complemento = endereco?.complemento,
    latitude = endereco?.latitude?.toString(),
    longitude = endereco?.longitude?.toString(),
    organizadorNome = null,
    organizadorEmail = null,
    artista = null
)
