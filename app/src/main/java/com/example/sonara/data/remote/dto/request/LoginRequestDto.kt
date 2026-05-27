package com.example.sonara.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class LoginRequestDto(
    @SerializedName("email") val email: String,
    @SerializedName("senha") val senha: String
)