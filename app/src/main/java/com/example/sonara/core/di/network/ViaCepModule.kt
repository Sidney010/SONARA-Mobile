package com.example.sonara.core.di.network

import com.example.sonara.data.remote.api.ViaCepApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViaCepModule {

    @Provides
    @Singleton
    @Named("viacep")
    fun provideViaCepRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://viacep.com.br/ws/")
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideViaCepApi(
        @Named("viacep")
        retrofit: Retrofit
    ): ViaCepApi {

        return retrofit.create(
            ViaCepApi::class.java
        )
    }
}