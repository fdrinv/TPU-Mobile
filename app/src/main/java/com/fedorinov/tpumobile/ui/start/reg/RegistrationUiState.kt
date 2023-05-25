package com.fedorinov.tpumobile.ui.start.reg

import com.fedorinov.tpumobile.data.common.UserInfo
import com.fedorinov.tpumobile.data.database.entity.GroupEntity

data class RegistrationUiState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val gender: Gender = Gender.MALE,
    val group: String = "",
    val groups: List<GroupEntity> = emptyList(),
    val language: Language = Language.getLanguage(),
    val phone: String = "",
    val isConsent: Boolean = false,
) {
    fun toUserInfo() = UserInfo(
        email = this.email,
        password = this.password,
        firstName = this.firstName,
        lastName = this.lastName,
        gender = this.gender,
        group = this.group,
        languageId = this.language.id,
        phone = this.phone
    )
}
