package com.example.sonara.core.di.repository

import com.example.sonara.data.repository.EnderecoRepositoryImpl
import com.example.sonara.data.repository.EventoArtistaRepositoryImpl
import com.example.sonara.data.repository.EventoRepositoryImpl
import com.example.sonara.data.repository.UsuarioRepositoryImpl
import com.example.sonara.domain.repository.EnderecoRepository
import com.example.sonara.domain.repository.EventoArtistaRepository
import com.example.sonara.domain.repository.EventoRepository
import com.example.sonara.domain.repository.GeneroMusicalRepository
import com.example.sonara.domain.repository.NacionalidadeRepository
import com.example.sonara.domain.repository.UsuarioRepository
import com.example.sonara.data.repository.RedeSocialRepositoryImpl
import com.example.sonara.domain.repository.RedeSocialRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton
    abstract fun bindUsuarioRepository(impl: UsuarioRepositoryImpl): UsuarioRepository

    @Binds @Singleton
    abstract fun bindRedeSocialRepository(impl: RedeSocialRepositoryImpl): RedeSocialRepository

    @Binds @Singleton
    abstract fun bindNacionalidadeRepository(impl: UsuarioRepositoryImpl): NacionalidadeRepository

    @Binds @Singleton
    abstract fun bindGeneroMusicalRepository(impl: UsuarioRepositoryImpl): GeneroMusicalRepository

    @Binds @Singleton
    abstract fun bindEnderecoRepository(impl: EnderecoRepositoryImpl): EnderecoRepository

    @Binds @Singleton
    abstract fun bindEventoRepository(impl: EventoRepositoryImpl): EventoRepository

    @Binds @Singleton
    abstract fun bindEventoArtistaRepository(impl: EventoArtistaRepositoryImpl): EventoArtistaRepository
}