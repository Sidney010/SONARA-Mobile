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
            if (body != null && body.status) {
                AppResult.Success(mapper(body.data))
            } else {
                AppResult.Error(
                    Exception(body?.message ?: "Erro na resposta da API")
                )
            }
        } else {
            val errorBody = response.errorBody()?.string()
            AppResult.Error(
                Exception("Erro HTTP ${response.code()}: $errorBody")
            )
        }
    } catch (e: Exception) {
        AppResult.Error(e)
    }
}

suspend fun <T, R> safeApiCallSimple(
    apiCall: suspend () -> Response<T>,
    mapper: (T) -> R
): AppResult<R> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                AppResult.Success(mapper(body))
            } else {
                AppResult.Error(Exception("Body vazio"))
            }
        } else {
            AppResult.Error(Exception("Erro ${response.code()}"))
        }
    } catch (e: Exception) {
        AppResult.Error(e)
    }
}