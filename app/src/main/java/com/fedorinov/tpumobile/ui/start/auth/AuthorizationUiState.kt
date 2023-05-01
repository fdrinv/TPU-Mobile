package com.fedorinov.tpumobile.ui.start.auth

import com.fedorinov.tpumobile.ui.start.LoginState

data class AuthorizationUiState(
    val login: String = "",
    val password: String = "",
    val isRemember: Boolean = false,
    val isHidePassword: Boolean = true,
    val loginState: LoginState = LoginState.Unknown
)
