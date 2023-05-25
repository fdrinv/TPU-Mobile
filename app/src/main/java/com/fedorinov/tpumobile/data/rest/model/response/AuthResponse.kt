package com.fedorinov.tpumobile.data.rest.model.response

import com.google.gson.annotations.SerializedName

/**
 * Ответ авторизации.
 * Пример:
 * {
 *  {
 *   "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlYWYyNUB0cHUucnUiLCJleHAiOjE2ODU0NjYwMDAsInNhbHQiOiJRY1RPKmJra0RFZTBRIn0.vPKLB2aUagevK1sUU_wOLNJSD0k55s7oCharaUQiI4rg9rnmm6RWpflIo0ctbDCGbsTcoEwMLge5wXUibdoxpg",
 *   "user": {
 *   "email": "eaf25@tpu.ru",
 *   "firstName": "Евгений",
 *   "lastName": "Федоринов",
 *   "gender": "Male",
 *   "phoneNumber": "+79138754824",
 *   "groupName": "8К91",
 *   "languageId": "E06ED586-DD3B-4751-9BED-764047793AFA",
 *   "languageName": "ru"
 *   }
 * }
 *
 * ИЛИ
 *
 * {
 *   "type": "BusinessError",
 *   "date": "2023-05-07 08:42:43 UTC",
 *   "message": "Указанный пользователь fedorinov@email.r не найден"
 * }
 */
data class AuthResponse(
    @SerializedName("token") val token: String? = null,
    @SerializedName("user") val user: UserResponse? = null,

    @SerializedName("type") val type: String? = null,
    @SerializedName("date") val date: String? = null,
    @SerializedName("message") val message: String? = null
)
