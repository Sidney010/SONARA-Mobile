package com.example.sonara.core.di.storage

import android.content.Context
import com.example.sonara.core.storage.FormDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun provideFormDataStore(
        @ApplicationContext context: Context
    ): FormDataStore {

        return FormDataStore(context)
    }
}