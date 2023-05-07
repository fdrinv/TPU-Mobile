package com.fedorinov.tpumobile.ui.start.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorinov.tpumobile.data.repositories.AuthRepository
import com.fedorinov.tpumobile.ui.start.auth.AuthorizationUiEvent.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TIME_VIEW_RESULT_MESSAGE = 3000L

class AuthorizationViewModel(private val authRepository: AuthRepository) : ViewModel() {

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
                is SignIn -> {
                    signIn()
                    uiState.value
                }
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
     */
    private fun signIn() = viewModelScope.launch {
        // 1. Начинаем крутить баранку, о том, что процесс авторизации стартовал
        _uiState.update { prevState ->
            prevState.copy(
                authState = AuthState.Loading
            )
        }
        // 2. Отправляем запрос на авторизацию и получаем результат
        val response = authRepository.authorization(
            email = uiState.value.login,
            password = uiState.value.password,
            rememberMe = uiState.value.isRemember
        )
        // 3.1. Если авторизация прошла успешно
        if (response.message.isEmpty()) {
            _uiState.update { prevState ->
                prevState.copy(
                    authState = AuthState.Success(response)
                )
            }
        }
        // 3.2 В противном случае вывести информацию по неудачной попытке авторизации.
        else {
            _uiState.update { prevState ->
                Log.i(TAG, "signIn: response = $response")
                prevState.copy(
                    authState = AuthState.Error(response)
                )
            }
        }
    }

    private fun changeRemember(prevState: AuthorizationUiState, isRemember: Boolean): AuthorizationUiState {
        return prevState.copy(
            isRemember = isRemember
        )
    }

    companion object {
        private val TAG = AuthorizationViewModel::class.simpleName
    }
}