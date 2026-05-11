package com.example.sonara.core.di.network

import com.example.sonara.data.remote.api.SonaraApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideSonaraApi(
        retrofit: Retrofit
    ): SonaraApi {

        return retrofit.create(
            SonaraApi::class.java
        )
    }
}