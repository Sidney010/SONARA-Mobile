package com.example.sonara.core.di.network

import com.example.sonara.core.network.NetworkConstants
import com.example.sonara.data.remote.api.geoapify.GeoapifyApiService
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
object GeoapifyModule {

    @Provides
    @Singleton
    @Named("geoapify")
    fun provideGeoapifyRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL_GEOAPIFY)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGeoapifyApiService(
        @Named("geoapify") retrofit: Retrofit
    ): GeoapifyApiService {
        return retrofit.create(GeoapifyApiService::class.java)
    }
}