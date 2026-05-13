package com.example.sonara.core.di.repository

import com.example.sonara.data.repository.EnderecoRepositoryImpl
import com.example.sonara.data.repository.UsuarioRepositoryImpl
import com.example.sonara.domain.repository.EnderecoRepository
import com.example.sonara.domain.repository.UsuarioRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUsuarioRepository(
        impl: UsuarioRepositoryImpl
    ): UsuarioRepository

    @Binds
    @Singleton
    abstract fun bindEnderecoRepository(
        impl: EnderecoRepositoryImpl
    ): EnderecoRepository
}