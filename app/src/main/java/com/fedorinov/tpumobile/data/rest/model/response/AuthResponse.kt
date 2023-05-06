package com.fedorinov.tpumobile.data.rest.model.response

import com.google.gson.annotations.SerializedName

/**
 * Ответ авторизации.
 */
data class AuthResponse(
    @SerializedName("type") val type: String,
    @SerializedName("date") val date: String,
    @SerializedName("message") val message: String
)
