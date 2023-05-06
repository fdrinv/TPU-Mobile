package com.fedorinov.tpumobile.ui.start.auth

import com.fedorinov.tpumobile.data.rest.model.response.AuthResponse

sealed class LoginState {
    data class Success(val text: String) : LoginState()
    data class Error(val authResponse: AuthResponse?) : LoginState()
    object Loading : LoginState()
    object Unknown : LoginState()
}
