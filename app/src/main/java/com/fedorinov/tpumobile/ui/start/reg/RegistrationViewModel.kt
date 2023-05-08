package com.fedorinov.tpumobile.ui.start.reg

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorinov.tpumobile.data.repositories.CommonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class RegistrationViewModel(private val commonRepository: CommonRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<RegistrationUiState> = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState

    init {
        // - Получаем список групп из бд и обновляем состояние экрана
        commonRepository.getAllGroupsAsFlow().onEach { groups ->
            _uiState.update { prevState ->
                prevState.copy(groups = groups)
            }
        }.launchIn(viewModelScope)
    }

    companion object {
        private val TAG = RegistrationViewModel::class.simpleName
    }

}