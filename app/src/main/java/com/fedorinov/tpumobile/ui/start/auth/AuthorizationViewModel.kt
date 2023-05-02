package com.fedorinov.tpumobile.ui.start.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorinov.tpumobile.ui.start.LoginState
import com.fedorinov.tpumobile.ui.start.auth.AuthorizationUiEvent.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthorizationViewModel : ViewModel() {

    // - Состояние экрана
    private val _uiState: MutableStateFlow<AuthorizationUiState> = MutableStateFlow(AuthorizationUiState())
    val uiState: StateFlow<AuthorizationUiState> = _uiState

    // - Обработчик событий экрана
    fun receiveUiEvent(currentEvent: AuthorizationUiEvent) = viewModelScope.launch {
        _uiState.update { prevState ->
            when(currentEvent) {
                is LoginChanged -> changeLogin(prevState, currentEvent.newLogin)
                is PasswordChanged -> changePassword(prevState, currentEvent.newPassword)
                is RememberChanged -> changeRemember(prevState, currentEvent.isRemember)
                is PasswordVisibilityChanged -> changePasswordVisibility(prevState)
                is SignIn -> signIn(prevState)
            }
        }
    }

    /**
     * Изменяет поле логина.
     * @param [prevState]   - предыдущее состояние.
     * @param [newLogin]    - новая строка логина.
     */
    private fun changeLogin(prevState: AuthorizationUiState, newLogin: String): AuthorizationUiState  {
        // TODO: Добавить валидацию данных
        return prevState.copy(
            login = newLogin
        )
    }

    /**
     * Изменяет поле логина.
     * @param [prevState]   - предыдущее состояние.
     * @param [newPassword] - новая строка пароля.
     */
    private fun changePassword(prevState: AuthorizationUiState, newPassword: String): AuthorizationUiState  {
        // TODO: Добавить валидацию данных
        return prevState.copy(
            password = newPassword
        )
    }

    /**
     * Меняет видимость символов в поле пароля.
     * @param [prevState] - предыдущее состояние.
     */
    private fun changePasswordVisibility(prevState: AuthorizationUiState): AuthorizationUiState {
        return prevState.copy(
            isHidePassword = !prevState.isHidePassword
        )
    }

    /**
     * Авторизация в системе.
     * @param [prevState] - предыдущее состояние.
     */
    private fun signIn(prevState: AuthorizationUiState): AuthorizationUiState {
        return prevState.copy(
            loginState = LoginState.Loading
        )
    }

    private fun changeRemember(prevState: AuthorizationUiState, isRemember: Boolean): AuthorizationUiState {
        return prevState.copy(
            isRemember = isRemember
        )
    }
}