package com.example.sonara.data.remote.datasource

import com.example.sonara.core.network.ApiResponse
import com.example.sonara.data.remote.api.SonaraApi
import com.example.sonara.data.remote.dto.request.CreateUsuarioRequestDto
import com.example.sonara.data.remote.dto.request.LoginRequestDto
import com.example.sonara.data.remote.dto.response.GeneroMusicalListDto
import com.example.sonara.data.remote.dto.response.LoginResponseDto
import com.example.sonara.data.remote.dto.response.NacionalidadeListDto
import com.example.sonara.data.remote.dto.response.UsuarioResponseDto
import retrofit2.Response
import javax.inject.Inject

class UsuarioRemoteDataSource @Inject constructor(
    private val api: SonaraApi
) {
    suspend fun register(
        request: CreateUsuarioRequestDto
    ): Response<ApiResponse<UsuarioResponseDto>> {
        return api.register(request)
    }

    suspend fun login(
        email: String,
        senha: String
    ): Response<LoginResponseDto> {
        return api.login(LoginRequestDto(email = email, senha = senha))
    }

    suspend fun getNacionalidades(): Response<ApiResponse<NacionalidadeListDto>> {
        return api.getNacionalidades()
    }

    suspend fun getGenerosMusicais(): Response<ApiResponse<GeneroMusicalListDto>> {
        return api.getGenerosMusicais()
    }
}