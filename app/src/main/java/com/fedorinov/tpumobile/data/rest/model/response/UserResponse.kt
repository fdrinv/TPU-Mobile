package com.fedorinov.tpumobile.data.rest.model.response

import com.google.gson.annotations.SerializedName

/**
 * "user": {
 *   "email": "eaf25@tpu.ru",
 *   "firstName": "Евгений",
 *   "lastName": "Федоринов",
 *   "gender": "Male",
 *   "phoneNumber": "+79138754824",
 *   "groupName": "8К91",
 *   "languageId": "E06ED586-DD3B-4751-9BED-764047793AFA",
 *   "languageName": "ru"
 *   }
 */
data class UserResponse(
    @SerializedName("email") val email: String? = null,
    @SerializedName("firstName") val firstName: String? = null,
    @SerializedName("lastName") val lastName: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("phoneNumber") val phoneNumber: String? = null,
    @SerializedName("groupName") val groupName: String? = null,
    @SerializedName("languageId") val languageId: String? = null,
    @SerializedName("languageName") val languageName: String? = null,
)
