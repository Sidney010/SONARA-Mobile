package com.example.sonara.data.remote.api

import com.example.sonara.core.network.ApiResponse
import com.example.sonara.data.remote.dto.request.CreateUsuarioRequestDto
import com.example.sonara.data.remote.dto.request.LoginRequestDto
import com.example.sonara.data.remote.dto.response.EventoListDto
import com.example.sonara.data.remote.dto.response.GeneroMusicalListDto
import com.example.sonara.data.remote.dto.response.LoginResponseDto
import com.example.sonara.data.remote.dto.response.NacionalidadeListDto
import com.example.sonara.data.remote.dto.response.UsuarioPerfilDto
import com.example.sonara.data.remote.dto.response.UsuarioResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SonaraApi {

    // ── Cadastro
    @POST("usuario/")
    suspend fun register(
        @Body request: CreateUsuarioRequestDto
    ): Response<ApiResponse<UsuarioResponseDto>>

    // ── Login
    // Resposta de login NÃO usa o wrapper ApiResponse — retorna body direto
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