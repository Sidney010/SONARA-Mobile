package com.example.sonara.domain.usecase

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.usuarioperfil.UsuarioPerfil
import com.example.sonara.domain.repository.UsuarioRepository
import javax.inject.Inject

class BuscarCandidaturasPorArtistaUseCase @Inject constructor(
    private val repository: UsuarioRepository
) {
    suspend operator fun invoke(artistaId: Int): AppResult<UsuarioPerfil> {
        // Na estrutura atual do Sonara, o perfil do usuário já contém as candidaturas (eventos) do artista.
        // O id_artista é geralmente associado ao id_usuario no fluxo do app.
        // Se precisarmos especificamente de candidaturas, o BuscarUsuarioPorId já traz 'eventos' no objeto 'artista'.
        return repository.buscarUsuarioPorId(artistaId)
    }
}
