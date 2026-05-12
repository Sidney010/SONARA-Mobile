package com.example.sonara.core.network

import com.example.sonara.core.common.AppResult
import retrofit2.Response

suspend fun <T, R> safeApiCall(
    apiCall: suspend () -> Response<ApiResponse<T>>,
    mapper: (T) -> R
): AppResult<R> {

    return try {

        val response = apiCall()

        if (response.isSuccessful) {

            val body = response.body()

            if (body != null) {

                AppResult.Success(
                    mapper(body.data)
                )

            } else {

                AppResult.Error(
                    Exception("Resposta vazia")
                )
            }

        } else {

            AppResult.Error(
                Exception("Erro HTTP: ${response.code()}")
            )
        }

    } catch (e: Exception) {

        AppResult.Error(e)
    }
}