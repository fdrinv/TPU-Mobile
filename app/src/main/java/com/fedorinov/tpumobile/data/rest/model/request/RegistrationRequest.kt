package com.fedorinov.tpumobile.data.rest.model.request

import com.google.gson.annotations.SerializedName
import java.util.Locale

/**
 * Модель для регистрации пользователя в системе.
 * {
"email": "eaf25@tpu.ru",
"password": "Qwerty1!",
"firstName": "Евгений",
"lastName": "Федоринов",
"groupName": "8К91",
"gender": "Male",
"selectedLanguage": "",
"languageId": "1",
"phoneNumber": "+79138754824"
}
 */
data class RegistrationRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String? = null,
    @SerializedName("groupName") val groupName: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("selectedLanguage") val selectedLanguage: String = Locale.getDefault().language,
    @SerializedName("languageId") val languageId: Int,
    @SerializedName("phoneNumber") val phoneNumber: String? = null
)
