package com.example.sonara.data.remote.api

import com.example.sonara.core.network.ApiResponse
import com.example.sonara.data.remote.dto.request.CandidaturaCreateRequestDto
import com.example.sonara.data.remote.dto.request.CandidaturaUpdateRequestDto
import com.example.sonara.data.remote.dto.request.EventoArtistaRequestDto
import com.example.sonara.data.remote.dto.request.EventoCreateRequestDto
import com.example.sonara.data.remote.dto.request.LoginRequestDto
import com.example.sonara.data.remote.dto.request.RedeSocialRequestDto
import com.example.sonara.data.remote.dto.response.candidatura.CandidaturaDto
import com.example.sonara.data.remote.dto.response.candidatura.CandidaturaListDto
import com.example.sonara.data.remote.dto.response.evento.EventoArtistaDto
import com.example.sonara.data.remote.dto.response.evento.EventoArtistaResponseDto
import com.example.sonara.data.remote.dto.response.evento.EventoDto
import com.example.sonara.data.remote.dto.response.evento.EventoResponseDto
import com.example.sonara.data.remote.dto.response.evento.EventoListDto
import com.example.sonara.data.remote.dto.response.evento.EventoSimpleDto
import com.example.sonara.data.remote.dto.response.foto.FotoResponseDto
import com.example.sonara.data.remote.dto.response.generomusicais.GeneroMusicalListDto
import com.example.sonara.data.remote.dto.response.login.LoginResponseDto
import com.example.sonara.data.remote.dto.response.nacionalidade.NacionalidadeListDto
import com.example.sonara.data.remote.dto.response.redesocial.RedeSocialDto
import com.example.sonara.data.remote.dto.response.redesocial.RedeSocialListDto
import com.example.sonara.data.remote.dto.response.redesocial.TipoRedeSocialListDto
import com.example.sonara.data.remote.dto.response.usuario.ArtistaListDto
import com.example.sonara.data.remote.dto.response.usuario.UsuarioResponseDto
import com.example.sonara.data.remote.dto.response.usuario.perfil.UsuarioPerfilResponseDto
import com.example.sonara.data.remote.dto.response.usuario.perfil.UsuarioPerfilWrapperDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface SonaraApi {

    @Multipart
    @POST("usuario/")
    suspend fun register(
        @Part foto: MultipartBody.Part?,
        @Part("dados") dados: RequestBody
    ): Response<ApiResponse<UsuarioResponseDto>>

    @POST("usuario/login")
    suspend fun login(@Body request: LoginRequestDto): Response<LoginResponseDto>

    @GET("usuario/{id}")
    suspend fun getUsuarioById(
        @Path("id") id: Int
    ): Response<ApiResponse<UsuarioPerfilWrapperDto>>

    @GET("evento")
    suspend fun getEventos(): Response<ApiResponse<EventoListDto>>

    @GET("evento/{id}")
    suspend fun getEventoById(@Path("id") id: Int): Response<ApiResponse<EventoResponseDto>>

    @GET("eventoArtista/{id}")
    suspend fun getEventoArtistaById(@Path("id") id: Int): Response<EventoArtistaResponseDto>

    @POST("eventoArtista")
    suspend fun criarEventoArtista(
        @Body request: EventoArtistaRequestDto
    ): Response<EventoArtistaResponseDto>

    @PUT("eventoArtista/{id}")
    suspend fun atualizarEventoArtista(
        @Path("id") id: Int,
        @Body request: EventoArtistaRequestDto
    ): Response<EventoArtistaResponseDto>

    @DELETE("eventoArtista/{id}")
    suspend fun deletarEventoArtista(@Path("id") id: Int): Response<ApiResponse<Unit>>

    @GET("evento/organizador/{organizadorId}")
    suspend fun getEventosPorOrganizador(
        @Path("organizadorId") organizadorId: Int
    ): Response<ApiResponse<EventoListDto>>

    @POST("evento")
    suspend fun criarEvento(
        @Body request: EventoCreateRequestDto
    ): Response<ApiResponse<EventoSimpleDto>>

    @PUT("evento/{id}")
    suspend fun editarEvento(
        @Path("id") id: Int,
        @Body request: EventoCreateRequestDto
    ): Response<ApiResponse<EventoSimpleDto>>

    @DELETE("evento/{id}")
    suspend fun deletarEvento(@Path("id") id: Int): Response<ApiResponse<Unit>>

    // ── Fotos do Evento ────────────────────────────────────────────────────────

    @Multipart
    @POST("foto")
    suspend fun uploadFotoEvento(
        @Part foto: MultipartBody.Part,
        @Part("evento_id") eventoId: RequestBody
    ): Response<ApiResponse<FotoResponseDto>>

    @DELETE("foto/{id}")
    suspend fun deletarFoto(@Path("id") id: Int): Response<ApiResponse<Unit>>

    // ── Redes Sociais ─────────────────────────────────────────────────────────

    @GET("redesSociais")
    suspend fun getRedesSociais(): Response<ApiResponse<RedeSocialListDto>>

    @GET("redesSociais/{id}")
    suspend fun getRedesSociaisPorUsuario(
        @Path("id") usuarioId: Int
    ): Response<ApiResponse<RedeSocialListDto>>

    @POST("redesSociais")
    suspend fun createRedeSocial(
        @Body request: RedeSocialRequestDto
    ): Response<ApiResponse<RedeSocialDto>>

    @PUT("redesSociais/{id}")
    suspend fun updateRedeSocial(
        @Path("id") id: Int,
        @Body request: RedeSocialRequestDto
    ): Response<ApiResponse<RedeSocialDto>>

    @DELETE("redesSociais/{id}")
    suspend fun deleteRedeSocial(@Path("id") id: Int): Response<ApiResponse<Unit>>

    // ── Tipo Redes Sociais ────────────────────────────────────────────────────

    @GET("TiporedesSociais")
    suspend fun getTiposRedesSociais(): Response<ApiResponse<TipoRedeSocialListDto>>

    // ── Candidatura ───────────────────────────────────────────────────────────

    @GET("candidatura/evento/{eventoId}")
    suspend fun getCandidaturasPorEvento(
        @Path("eventoId") eventoId: Int
    ): Response<ApiResponse<CandidaturaListDto>>

    @GET("candidatura/artista/{artistaId}")
    suspend fun getCandidaturasPorArtista(
        @Path("artistaId") artistaId: Int
    ): Response<ApiResponse<CandidaturaListDto>>

    @POST("eventoArtista/candidatar/")
    suspend fun criarCandidatura(
        @Body request: CandidaturaCreateRequestDto
    ): Response<ApiResponse<CandidaturaDto>>

    @PUT("eventoArtista/aceitarConvite/{id}")
    suspend fun aceitarConvite(
        @Path("id") id: Int
    ): Response<ApiResponse<CandidaturaDto>>

    @PUT("eventoArtista/recusarConvite/{id}")
    suspend fun recusarConvite(
        @Path("id") id: Int
    ): Response<ApiResponse<CandidaturaDto>>

    @PUT("candidatura/{id}")
    suspend fun atualizarCandidatura(
        @Path("id") id: Int,
        @Body request: CandidaturaUpdateRequestDto
    ): Response<ApiResponse<CandidaturaDto>>

    // ── Catálogos ─────────────────────────────────────────────────────────────

    @GET("nacionalidade")
    suspend fun getNacionalidades(): Response<ApiResponse<NacionalidadeListDto>>

    @GET("generoMusical")
    suspend fun getGenerosMusicais(): Response<ApiResponse<GeneroMusicalListDto>>

    @GET("artista")
    suspend fun getArtistas(): Response<ApiResponse<ArtistaListDto>>
}