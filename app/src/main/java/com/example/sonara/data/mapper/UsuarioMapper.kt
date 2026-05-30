package com.example.sonara.data.mapper

import com.example.sonara.data.remote.dto.response.usuario.perfil.UsuarioPerfilArtistaDto
import com.example.sonara.data.remote.dto.response.usuario.perfil.UsuarioPerfilDto
import com.example.sonara.data.remote.dto.response.usuario.perfil.UsuarioPerfilOrganizadorDto
import com.example.sonara.domain.model.Endereco
import com.example.sonara.domain.model.Genero
import com.example.sonara.domain.model.GeneroMusical
import com.example.sonara.domain.model.Nacionalidade
import com.example.sonara.domain.model.RedeSocial
import com.example.sonara.domain.model.usuarioperfil.*
import com.example.sonara.data.remote.dto.request.CreateUsuarioRequestDto
import com.example.sonara.data.remote.dto.response.login.LoginResponseDto
import com.example.sonara.data.remote.dto.response.usuario.UsuarioResponseDto
import com.example.sonara.data.remote.dto.response.usuario.perfil.UsuarioPerfilEventosDto
import com.example.sonara.domain.model.LoginResult
import com.example.sonara.domain.model.Usuario
import com.example.sonara.domain.model.UsuarioLogin
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// ── Usuario → Request DTO ─────────────────────────────────────────────────────
// CORREÇÃO: o mapper anterior ignorava a maioria dos campos obrigatórios da API
fun Usuario.toRequestDto() = CreateUsuarioRequestDto(
    nome              = nome,
    email             = email,
    senha             = senha,
    cpf               = cpf.filter { it.isDigit() },             // remove máscara
    data_nasc         = dataNascimento,                          // "yyyy-MM-dd"
    nacionalidade_id  = nacionalidadeId ?: 9,                    // default: Brasileiro
    genero_id         = generoId ?: 0,
    criado            = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
    ultima_atualizacao= LocalDate.now().format(DateTimeFormatter.ISO_DATE),
    telefone          = telefone ?: "",
    tipo_usuario      = tipoUsuario,
    nome_artistico    = nomeArtistico,
    descricao         = descricao,
    cep               = cep ?: "",
    cidade            = cidade ?: "",
    estado            = estado ?: "",
    logradouro        = logradouro ?: "",
    numero            = numero,
    complemento       = complemento,
    bairro            = bairro ?: "",
    longitude         = longitude,
    latitude          = latitude,
    generos_musicais  = generosMusicais
)


fun UsuarioResponseDto.toDomain() = Usuario(
    id             = idUsuario,
    nome           = nome,
    email          = email,
    senha          = "",
    cpf            = cpf,
    dataNascimento =  "",
    fotoPerfil     = ""
)
fun UsuarioPerfilDto.toDomain() = UsuarioPerfil(
    idUsuario         = idUsuario,
    nome              = nome ?: "",
    email             = email ?: "",
    cpf               = cpf,
    dataNasc          = dataNasc,
    telefone          = telefone,
    foto              = foto,
    criado            = criado,
    ultimaAtualizacao = ultimaAtualizacao,
    tipoUsuario       = tipoUsuario ?: "Usuario",
    genero            = genero?.let {
        Genero(it.idGenero ?: 0, it.nome ?: "")
    },
    nacionalidade     = nacionalidade?.let {
        Nacionalidade(it.idNacionalidade ?: 0, it.nome ?: "")
    },
    endereco          = endereco?.let {
        Endereco(
            cep       = it.cep ?: "",
            rua       = it.logradouro ?: "",
            bairro    = it.bairro ?: "",
            cidade    = it.cidade ?: "",
            uf        = it.estado ?: "",
            latitude  = it.latitude ?: "",
            longitude = it.longitude ?: ""
        )
    },
    redesSociais = redesSociais?.mapNotNull { rs ->
        // link é não-nulo no RedeSocial domain — proteger
        rs.link.let {
            RedeSocial(
                id       = rs.idRedesSociais,
                link     = rs.link,
                tipoId   = rs.tipoId,
                tipoNome = rs.tipo,
                usuarioId = rs.usuarioId
            )
        }
    } ?: emptyList(),
    artista     = artista?.toDomain(),
    organizador = organizador
)

private fun UsuarioPerfilArtistaDto.toDomain() = UsuarioArtistaPerfil(
    idAtista        = idAtista,
    nomeArtistico   = nomeArtistico,
    descricao       = descricao,
    generosMusicais = generosMusicais.mapNotNull { g ->
        g?.let { GeneroMusical(it.id_genero_musical, it.nome) }
    },
    mediaAvaliacao  = mediaAvaliacao,
    totalAvaliacoes = totalAvaliacoes,
    eventos         = eventos.mapNotNull { it?.toDomain() }
)

private fun UsuarioPerfilEventosDto.toDomain() = UsuarioEventoPerfil(
    idEvento        = idEvento,
    eventoData      = eventoData,
    eventoNome      = eventoNome,
    cache           = cache?.let {
        UsuarioCachePerfil(
            cacheFinal    = it.cacheFinal,
            cacheEsperado = it.cacheEsperado,
            cacheOfertado = it.cacheOfertado,
            cacheProposta = it.cacheProposta
        )
    },
    status          = status?.nome,
    fotos           = fotos?.mapNotNull { f ->
        f?.url?.let { url -> UsuarioFotosPerfil(f.idFoto, url) }
    },
    endereco        = endereco?.let {
        UsuarioPerfilEventosEndereco(
            cep              = it.cep,
            bairro           = it.bairro,
            cidade           = it.cidade,
            estado           = it.estado,
            numero           = it.numero,
            latitude         = it.latitude,
            longitude        = it.longitude,
            logradouro       = it.logradouro,
            complemento      = it.complemento,
            idEnderecoEvento = it.idEnderecoEvento
        )
    },
    horaFim         = horaFim,
    descricao       = descricao,
    horaInicio      = horaInicio,
    sobreArtista    = sobreArtista,
    motivoInscricao = motivoInscricao,
    idEventoArtista = idEventoArtista
)

// ── Login Response → Domain ──────────────────────────────────────────────────
fun LoginResponseDto.toDomain() = LoginResult(
    token = token,
    usuario = UsuarioLogin(
        idUsuario = usuario.idUsuario,
        nome      = usuario.nome,
        email     = usuario.email,
        foto      = usuario.foto,
        tipoUsuario  = usuario.tipoUsuario,
        idArtista    = usuario.idArtista,
        idOrganizador = usuario.idOrganizador
    )
)