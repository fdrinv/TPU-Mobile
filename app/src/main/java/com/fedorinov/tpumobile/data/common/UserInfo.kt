package com.fedorinov.tpumobile.data.common

import com.fedorinov.tpumobile.data.rest.model.request.RegistrationRequest
import com.fedorinov.tpumobile.ui.start.reg.Gender
import java.util.Locale

/**
 * Содержит в себе информацию о пользователе.
 * Практически дублирует класс RegistrationUiState.
 */
data class UserInfo(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String?,
    val gender: Gender?,
    val group: String?,
    val languageId: Int,
    val phone: String?,
) {
    fun toRegistrationRequest() = RegistrationRequest(
        email = this.email,
        password = this.password,
        firstName = this.firstName,
        lastName = this.lastName,
        groupName = this.group,
        gender = this.gender?.enName,
        selectedLanguage = Locale.getDefault().language,
        languageId = this.languageId,
        phoneNumber = this.phone
    )
}
