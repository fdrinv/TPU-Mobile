package com.fedorinov.tpumobile.ui.start.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            prevState
        }
    }
}