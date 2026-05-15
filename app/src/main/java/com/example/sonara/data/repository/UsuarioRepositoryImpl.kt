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
import com.example.sonara.domain.repository.GeneroMusicalRepository
import com.example.sonara.domain.repository.NacionalidadeRepository
import com.example.sonara.domain.repository.UsuarioRepository
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val remoteDataSource: UsuarioRemoteDataSource
) : UsuarioRepository, NacionalidadeRepository, GeneroMusicalRepository {

    // ── Cadastro ──────────────────────────────────────────────────────────────
    override suspend fun register(user: Usuario): AppResult<Usuario> {
        return safeApiCall(
            apiCall = { remoteDataSource.register(user.toRequestDto()) },
            mapper  = { it.toDomain() }
        )
    }

    // ── Login ─────────────────────────────────────────────────────────────────
    // A API de login retorna o body diretamente (sem wrapper ApiResponse)
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
                val code = response.code()
                AppResult.Error(
                    Exception(
                        when (code) {
                            401 -> "Email ou senha incorretos"
                            404 -> "Usuário não encontrado"
                            else -> "Erro ${code}"
                        }
                    )
                )
            }
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }

    // ── Catálogos
    override suspend fun listarNacionalidades(): AppResult<List<Nacionalidade>> {
        return safeApiCall(
            apiCall = { remoteDataSource.getNacionalidades() },
            mapper  = { dto -> dto.nacionalidades.map { it.toDomain() } }
        )
    }

    override suspend fun listarGeneroMusical(): AppResult<List<GeneroMusical>> {
        return safeApiCall(
            apiCall = { remoteDataSource.getGenerosMusicais() },
            mapper  = { dto -> dto.generoMusical.map { it.toDomain() } }
        )
    }
}