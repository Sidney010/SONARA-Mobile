package com.example.sonara.data.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.core.network.safeApiCall
import com.example.sonara.data.mapper.toDomain
import com.example.sonara.data.mapper.toRequestDto
import com.example.sonara.data.remote.datasource.UsuarioRemoteDataSource
import com.example.sonara.domain.model.GeneroMusical
import com.example.sonara.domain.model.LoginResult
import com.example.sonara.domain.model.Nacionalidade
import com.example.sonara.domain.model.Usuario
import com.example.sonara.domain.model.usuarioperfil.UsuarioPerfil
import com.example.sonara.domain.repository.GeneroMusicalRepository
import com.example.sonara.domain.repository.NacionalidadeRepository
import com.example.sonara.domain.repository.UsuarioRepository
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val remoteDataSource: UsuarioRemoteDataSource
) : UsuarioRepository, NacionalidadeRepository, GeneroMusicalRepository {


    override suspend fun register(user: Usuario, photoFilePath: String?): AppResult<Usuario> {


        val dto      = user.toRequestDto()
        val json     = Gson().toJson(dto)
        val dadosPart = json.toRequestBody("text/plain".toMediaTypeOrNull())

        val fotoPart: MultipartBody.Part? = photoFilePath?.let { path ->
            val file = File(path)
            if (file.exists()) {
                val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("foto", file.name, requestBody)
            } else null
        }

        return safeApiCall(
            apiCall = { remoteDataSource.register(fotoPart, dadosPart) },
            mapper  = { it.toDomain() }
        )
    }

    override suspend fun login(email: String, senha: String): AppResult<LoginResult> {
        return try {
            val response = remoteDataSource.login(email, senha)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.status) {
                    AppResult.Success(body.toDomain())
                } else {
                    AppResult.Error(Exception("Credenciais inválidas"))
                }
            } else {
                AppResult.Error(Exception(when (response.code()) {
                    401  -> "Email ou senha incorretos"
                    404  -> "Usuário não encontrado"
                    else -> "Erro ${response.code()}"
                }))
            }
        } catch (e: Exception) { AppResult.Error(e) }
    }


    override suspend fun listarNacionalidades(): AppResult<List<Nacionalidade>> =
        safeApiCall(
            apiCall = { remoteDataSource.getNacionalidades() },
            mapper  = { dto -> dto.nacionalidades.map { it.toDomain() } }
        )

    override suspend fun listarGeneroMusical(): AppResult<List<GeneroMusical>> =
        safeApiCall(
            apiCall = { remoteDataSource.getGenerosMusicais() },
            mapper  = { dto -> dto.generoMusical.map { it.toDomain() } }
        )

    override suspend fun buscarUsuarioPorId(id: Int): AppResult<UsuarioPerfil> {
        return safeApiCall(
            apiCall = { remoteDataSource.getUsuarioById(id) },
            mapper  = { it.toDomain() }
        )
    }
}