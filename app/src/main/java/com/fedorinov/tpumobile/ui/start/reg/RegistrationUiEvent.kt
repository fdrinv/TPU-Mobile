package com.fedorinov.tpumobile.ui.start.reg

import com.fedorinov.tpumobile.data.common.UserInfo
import com.fedorinov.tpumobile.ui.start.reg.RegistrationUiEvent.EmailChanged
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * События экрана RegistrationScreen.
 *
 * [EmailChanged]    - Изменение поля почты.
 * [PasswordChanged] - Изменение поля пароля.
 * [RepeatPasswordChanged] - Изменение поля повторный пароль.
 * [FirstNameChanged] - Изменение поля имени.
 * [LastNameChanged] - Изменение поля фамилии.
 * [GenderChanged] - Выбор гендера.
 * [GroupChanged] - Выбор группы (изменение поля группы).
 * [LanguageChanged] - Выбор языка.
 * [PhoneChanged] - Изменение номера телефона.
 * [ConsentChanged] - Флаг соглашения.
 * [CreateAccount] - Создание аккаунта.
 *
 */
sealed class RegistrationUiEvent {
    data class EmailChanged(val newEmail: String) : RegistrationUiEvent()
    data class PasswordChanged(val newPassword: String) : RegistrationUiEvent()
    data class RepeatPasswordChanged(val newRepeatPassword: String) : RegistrationUiEvent()
    data class FirstNameChanged(val newFirstName: String) : RegistrationUiEvent()
    data class LastNameChanged(val newLastName: String) : RegistrationUiEvent()
    data class GenderChanged(val gender: Gender) : RegistrationUiEvent()
    data class GroupChanged(val group: String) : RegistrationUiEvent()
    data class LanguageChanged(val language: Language) : RegistrationUiEvent()
    data class PhoneChanged(val newPhone: String) : RegistrationUiEvent()
    data class ConsentChanged(val isConsent: Boolean) : RegistrationUiEvent()
    data class CreateAccount(val navigator: DestinationsNavigator) : RegistrationUiEvent()
}
