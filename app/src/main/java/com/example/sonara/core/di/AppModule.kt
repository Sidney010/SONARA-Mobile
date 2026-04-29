package com.example.sonara.core.di

import android.content.Context
import com.example.sonara.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideProcessImageUseCase() = ProcessImageUseCase()

    @Provides
    fun provideSaveFormUseCase(
        @ApplicationContext context: Context
    ) = SaveFormUseCase(context)

    @Provides
    fun provideGetFormUseCase(
        @ApplicationContext context: Context
    ) = GetFormUseCase(context)

    @Provides
    fun provideClearFormUseCase(
        @ApplicationContext context: Context
    ) = ClearFormUseCase(context)
}