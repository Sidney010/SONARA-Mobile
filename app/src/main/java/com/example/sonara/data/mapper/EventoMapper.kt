package com.example.sonara.data.mapper

import com.example.sonara.data.remote.dto.response.EventoDto
import com.example.sonara.data.remote.dto.response.UsuarioPerfilDto
import com.example.sonara.domain.model.Evento
import com.example.sonara.domain.model.UsuarioPerfil

fun EventoDto.toDomain() = Evento(
    id              = id_evento,
    nome            = evento_nome,
    descricao       = descricao,
    local           = local,
    data            = data,
    horaInicio      = hora_inicio,
    horaFim         = hora_fim,
    cep             = cep,
    logradouro      = logradouro,
    numero          = numero,
    complemento     = complemento,
    bairro          = bairro,
    cidade          = cidade,
    estado          = estado,
    latitude        = latitude,
    longitude       = longitude,
    statusAtual     = status_atual,
    organizadorNome = organizador_nome,
    organizadorEmail= organizador_email,
    artista         = artista,
    cacheFinal      = cache_final,
    sobreArtista    = sobre_artista,
    mediaAvaliacao  = media_avaliacao,
    totalAvaliacoes = total_avaliacoes,
    // filtra fotos com caminho nulo
    fotosUrls       = fotos.mapNotNull { it.caminho?.takeIf { c -> c.isNotBlank() } }
)

fun UsuarioPerfilDto.toDomain() = UsuarioPerfil(
    id             = id_usuario,
    nome           = nome,
    email          = email,
    cpf            = cpf,
    dataNasc       = data_nasc,
    telefone       = telefone,
    nacionalidadeId= nacionalidade_id,
    generoId       = genero_id,
    tipoUsuario    = tipo_usuario,
    nomeArtistico  = nome_artistico,
    descricao      = descricao,
    criado         = criado,
    fotosUrls      = fotos?.mapNotNull { it.caminho?.takeIf { c -> c.isNotBlank() } } ?: emptyList()
)