package com.example.sonara.core.network

import com.example.sonara.core.common.AppResult
import retrofit2.Response

suspend fun <T, R> safeApiCallSimple(

    apiCall: suspend () -> Response<T>,

    mapper: (T) -> R

): AppResult<R> {

    return try {

        val response = apiCall()

        if (response.isSuccessful) {

            val body = response.body()

            if (body != null) {

                AppResult.Success(
                    mapper(body)
                )

            } else {

                AppResult.Error(
                    Exception("Body vazio")
                )
            }

        } else {

            AppResult.Error(
                Exception("Erro ${response.code()}")
            )
        }

    } catch (e: Exception) {

        AppResult.Error(e)
    }
}