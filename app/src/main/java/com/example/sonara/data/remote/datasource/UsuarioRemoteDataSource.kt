package com.example.sonara.data.remote.datasource

import com.example.sonara.core.network.ApiResponse
import com.example.sonara.data.remote.api.SonaraApi
import com.example.sonara.data.remote.dto.request.LoginRequestDto
import com.example.sonara.data.remote.dto.response.generomusicais.GeneroMusicalListDto
import com.example.sonara.data.remote.dto.response.login.LoginResponseDto
import com.example.sonara.data.remote.dto.response.nacionalidade.NacionalidadeListDto
import com.example.sonara.data.remote.dto.response.usuario.UsuarioResponseDto

import com.example.sonara.data.remote.dto.response.usuario.perfil.UsuarioPerfilResponseDto
import com.example.sonara.data.remote.dto.response.usuario.perfil.UsuarioPerfilWrapperDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class UsuarioRemoteDataSource @Inject constructor(
    private val api: SonaraApi
) {
    /** Cadastro como multipart: foto (opcional) + JSON de dados */
    suspend fun register(
        foto: MultipartBody.Part?,
        dados: RequestBody
    ): Response<ApiResponse<UsuarioResponseDto>> =
        api.register(foto, dados)

    suspend fun login(
        email: String,
        senha: String
    ): Response<LoginResponseDto> =
        api.login(LoginRequestDto(email = email, senha = senha))

    suspend fun getNacionalidades(): Response<ApiResponse<NacionalidadeListDto>> =
        api.getNacionalidades()

    suspend fun getGenerosMusicais(): Response<ApiResponse<GeneroMusicalListDto>> =
        api.getGenerosMusicais()

    suspend fun getUsuarioById(id: Int): Response<ApiResponse<UsuarioPerfilWrapperDto>> =
        api.getUsuarioById(id)

    suspend fun getArtistas(): Response<ApiResponse<com.example.sonara.data.remote.dto.response.usuario.ArtistaListDto>> =
        api.getArtistas()
}