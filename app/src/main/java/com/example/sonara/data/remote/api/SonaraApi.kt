package com.example.sonara.data.remote.api

import com.example.sonara.core.network.ApiResponse
import com.example.sonara.data.remote.dto.request.CreateUsuarioRequestDto
import com.example.sonara.data.remote.dto.request.LoginRequestDto
import com.example.sonara.data.remote.dto.response.GeneroMusicalListDto
import com.example.sonara.data.remote.dto.response.LoginResponseDto
import com.example.sonara.data.remote.dto.response.NacionalidadeListDto
import com.example.sonara.data.remote.dto.response.UsuarioResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SonaraApi {

    // ── Cadastro
    @POST("usuario/")
    suspend fun register(
        @Body request: CreateUsuarioRequestDto
    ): Response<ApiResponse<UsuarioResponseDto>>

    // Login
    // A resposta de login NÃO usa o wrapper ApiResponse — vem direto no body
    @POST("usuario/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): Response<LoginResponseDto>

    // ── Catálogos
    @GET("nacionalidade")
    suspend fun getNacionalidades(): Response<ApiResponse<NacionalidadeListDto>>

    @GET("generoMusical")
    suspend fun getGenerosMusicais(): Response<ApiResponse<GeneroMusicalListDto>>
}