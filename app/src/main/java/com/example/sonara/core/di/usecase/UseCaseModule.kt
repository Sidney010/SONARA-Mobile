package com.example.sonara.core.di.usecase

import com.example.sonara.core.storage.FormDataStore
import com.example.sonara.domain.usecase.ClearFormUseCase
import com.example.sonara.domain.usecase.GetFormUseCase
import com.example.sonara.domain.usecase.ProcessImageUseCase
import com.example.sonara.domain.usecase.SaveFormUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideProcessImageUseCase() =
        ProcessImageUseCase()

    @Provides
    fun provideSaveFormUseCase(
        store: FormDataStore
    ) = SaveFormUseCase(store)

    @Provides
    fun provideGetFormUseCase(
        store: FormDataStore
    ) = GetFormUseCase(store)

    @Provides
    fun provideClearFormUseCase(
        store: FormDataStore
    ) = ClearFormUseCase(store)
}