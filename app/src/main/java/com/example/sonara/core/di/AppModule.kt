package com.example.sonara.core.di

import android.content.Context
import com.example.sonara.core.storage.FormDataStore
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
    fun provideFormDataStore(
        @ApplicationContext context: Context
    ): FormDataStore {
        return FormDataStore(context)
    }

    @Provides
    fun provideProcessImageUseCase() = ProcessImageUseCase()

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