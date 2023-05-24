package com.fedorinov.tpumobile.data.rest.model.response

import com.google.gson.annotations.SerializedName

/**
 * Ответ авторизации.
 * Пример:
 * {
 *   "type": "BusinessError",
 *   "date": "2023-05-07 08:42:43 UTC",
 *   "message": "Адрес электронной почты eaf25@tpu.ru уже занят"
 * }
 */
data class RegistrationResponse(
    @SerializedName("type") val type: String = "",
    @SerializedName("date") val date: String = "",
    @SerializedName("message") val message: String = ""
)
