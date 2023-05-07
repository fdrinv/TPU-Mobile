package com.fedorinov.tpumobile.ui.start.auth

import com.fedorinov.tpumobile.data.rest.model.response.AuthResponse

sealed class AuthState {
    data class Success(val authResponse: AuthResponse?) : AuthState()
    data class Error(val authResponse: AuthResponse?) : AuthState()
    object Loading : AuthState()
    object Unknown : AuthState()
}
