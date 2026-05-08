package com.example.sonara.data.remote

import com.example.sonara.data.remote.dto.UsuarioRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SonaraApi {
    @POST("usuarios")
    suspend fun registrarUsuario(@Body request: UsuarioRequest): Response<Unit>
}