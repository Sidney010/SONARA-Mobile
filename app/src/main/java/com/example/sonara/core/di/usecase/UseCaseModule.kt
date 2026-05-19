package com.example.sonara.core.di.usecase

import com.example.sonara.core.storage.FormDataStore
import com.example.sonara.domain.repository.EnderecoRepository
import com.example.sonara.domain.repository.EventoRepository
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

    @Provides fun provideProcessImageUseCase()                                 = ProcessImageUseCase()
    @Provides fun provideSaveFormUseCase(s: FormDataStore)                     = SaveFormUseCase(s)
    @Provides fun provideGetFormUseCase(s: FormDataStore)                      = GetFormUseCase(s)
    @Provides fun provideClearFormUseCase(s: FormDataStore)                    = ClearFormUseCase(s)
    @Provides fun provideRegisterUserUseCase(r: UsuarioRepository)             = RegisterUserUseCase(r)
    @Provides fun provideLoginUseCase(r: UsuarioRepository)                    = LoginUseCase(r)
    @Provides fun provideBuscarEnderecoPorCepUseCase(r: EnderecoRepository)    = BuscarEnderecoPorCepUseCase(r)
    @Provides fun provideListarNacionalidadesUseCase(r: NacionalidadeRepository) = ListarNacionalidadesUseCase(r)
    @Provides fun provideListarGenerosMusicaisUseCase(r: GeneroMusicalRepository) = ListarGenerosMusicaisUseCase(r)
    @Provides fun provideListarEventosUseCase(r: EventoRepository)             = ListarEventosUseCase(r)
    @Provides fun provideBuscarUsuarioPorIdUseCase(r: EventoRepository)        = BuscarUsuarioPorIdUseCase(r)
}