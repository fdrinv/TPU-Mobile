package com.fedorinov.tpumobile.ui.start.auth

/**
 * События экрана AuthorizationScreen.
 *
 * [LoginChanged]               - Изменение поля логина.
 * [PasswordChanged]            - Изменение поля пароля.
 * [RememberChanged]            - Изменение флага запоминания пользователя.
 * [PasswordVisibilityChanged]  - Изменение видимости поля пароля.
 * [SignIn]                     - Авторизация в системе.
 */
sealed class AuthorizationUiEvent {
    data class LoginChanged(val newLogin: String) : AuthorizationUiEvent()
    data class PasswordChanged(val newPassword: String) : AuthorizationUiEvent()
    data class RememberChanged(val isRemember: Boolean) : AuthorizationUiEvent()
    object PasswordVisibilityChanged : AuthorizationUiEvent()
    object SignIn : AuthorizationUiEvent()
}
