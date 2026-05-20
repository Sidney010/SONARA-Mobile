package com.example.sonara.data.remote.api

import com.example.sonara.core.network.ApiResponse
import com.example.sonara.data.remote.dto.request.LoginRequestDto
import com.example.sonara.data.remote.dto.response.EventoListDto
import com.example.sonara.data.remote.dto.response.GeneroMusicalListDto
import com.example.sonara.data.remote.dto.response.LoginResponseDto
import com.example.sonara.data.remote.dto.response.NacionalidadeListDto
import com.example.sonara.data.remote.dto.response.UsuarioPerfilDto
import com.example.sonara.data.remote.dto.response.UsuarioResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface SonaraApi {

    // ── Cadastro — FormData: foto (file) + dados (JSON text)
    @Multipart
    @POST("usuario/")
    suspend fun register(
        @Part foto: MultipartBody.Part?,
        @Part("dados") dados: RequestBody
    ): Response<ApiResponse<UsuarioResponseDto>>

    // ── Login (body direto, sem wrapper ApiResponse)
    @POST("usuario/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): Response<LoginResponseDto>

    // ── Perfil do usuário
    @GET("usuario/{id}")
    suspend fun getUsuarioById(
        @Path("id") id: Int
    ): Response<ApiResponse<UsuarioPerfilDto>>

    // ── Eventos
    @GET("evento")
    suspend fun getEventos(): Response<ApiResponse<EventoListDto>>

    // ── Catálogos
    @GET("nacionalidade")
    suspend fun getNacionalidades(): Response<ApiResponse<NacionalidadeListDto>>

    @GET("generoMusical")
    suspend fun getGenerosMusicais(): Response<ApiResponse<GeneroMusicalListDto>>
}