package com.fedorinov.tpumobile.ui.start.auth

data class AuthorizationUiState(
    val login: String = "",
    val password: String = "",
    val isRemember: Boolean = false,
    val isHidePassword: Boolean = true,
    val authState: AuthState = AuthState.Unknown
)
