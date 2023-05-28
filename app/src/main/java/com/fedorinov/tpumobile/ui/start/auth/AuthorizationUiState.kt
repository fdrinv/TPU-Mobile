package com.fedorinov.tpumobile.ui.start.auth

data class AuthorizationUiState(
    val login: String = "eaf25@tpu.ru",
    val password: String = "Qwerty1!",
    val isRemember: Boolean = false,
    val authState: AuthState = AuthState.Unknown
)
