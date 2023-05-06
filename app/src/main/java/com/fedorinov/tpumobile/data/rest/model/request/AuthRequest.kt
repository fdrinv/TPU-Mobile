package com.fedorinov.tpumobile.data.rest.model.request

import com.google.gson.annotations.SerializedName

/**
 * Модель для отправки данных о пользователя на авторизацию в системе.
 */
data class AuthRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("rememberMe") val rememberMe: Boolean
)
