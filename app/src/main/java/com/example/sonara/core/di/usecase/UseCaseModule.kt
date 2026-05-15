package com.example.sonara.core.di.usecase

import com.example.sonara.core.storage.FormDataStore
import com.example.sonara.domain.repository.EnderecoRepository
import com.example.sonara.domain.repository.GeneroMusicalRepository
import com.example.sonara.domain.repository.NacionalidadeRepository
import com.example.sonara.domain.repository.UsuarioRepository
import com.example.sonara.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideProcessImageUseCase() = ProcessImageUseCase()

    @Provides
    fun provideSaveFormUseCase(store: FormDataStore) = SaveFormUseCase(store)

    @Provides
    fun provideGetFormUseCase(store: FormDataStore) = GetFormUseCase(store)

    @Provides
    fun provideClearFormUseCase(store: FormDataStore) = ClearFormUseCase(store)

    @Provides
    fun provideRegisterUserUseCase(repo: UsuarioRepository) = RegisterUserUseCase(repo)

    @Provides
    fun provideLoginUseCase(repo: UsuarioRepository) = LoginUseCase(repo)

    @Provides
    fun provideBuscarEnderecoPorCepUseCase(repo: EnderecoRepository) =
        BuscarEnderecoPorCepUseCase(repo)

    @Provides
    fun provideListarNacionalidadesUseCase(repo: NacionalidadeRepository) =
        ListarNacionalidadesUseCase(repo)

    @Provides
    fun provideListarGenerosMusicaisUseCase(repo: GeneroMusicalRepository) =
        ListarGenerosMusicaisUseCase(repo)
}