package com.example.sonara.core.network

import com.example.sonara.core.common.AppResult
import retrofit2.Response

suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<ApiResponse<T>>
): AppResult<T> {

    return try {

        val response = apiCall()

        if (response.isSuccessful) {

            val body = response.body()

            if (
                body != null &&
                body.success &&
                body.data != null
            ) {

                AppResult.Success(body.data)

            } else {

                AppResult.Error(
                    Exception(
                        body?.message
                            ?: "Erro desconhecido"
                    )
                )
            }

        } else {

            AppResult.Error(
                Exception(
                    "Erro HTTP ${response.code()}"
                )
            )
        }

    } catch (e: Exception) {

        AppResult.Error(e)
    }
}