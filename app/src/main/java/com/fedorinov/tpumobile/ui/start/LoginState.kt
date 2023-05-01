package com.fedorinov.tpumobile.ui.start

sealed class LoginState {
    data class Success(val text: String) : LoginState()
    data class Error(val text: String) : LoginState()
    object Loading : LoginState()
    object Unknown : LoginState()
}
