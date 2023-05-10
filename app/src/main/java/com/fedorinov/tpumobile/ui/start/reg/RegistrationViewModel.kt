package com.fedorinov.tpumobile.ui.start.reg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorinov.tpumobile.data.repositories.CommonRepository
import com.fedorinov.tpumobile.logic.utils.checkLastCharForRegex
import com.fedorinov.tpumobile.logic.utils.numberPhoneRegex
import com.fedorinov.tpumobile.ui.start.reg.RegistrationUiEvent.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationViewModel(private val commonRepository: CommonRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<RegistrationUiState> = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState

    // - Обработчик событий экрана
    fun receiveUiEvent(currentEvent: RegistrationUiEvent) = viewModelScope.launch {
        _uiState.update { prevState ->
            when(currentEvent) {
                is EmailChanged -> changeEmail(prevState, currentEvent.newEmail)
                is PasswordChanged -> changePassword(prevState, currentEvent.newPassword)
                is RepeatPasswordChanged -> changeRepeatPassword(prevState, currentEvent.newRepeatPassword)
                is FirstNameChanged -> changeFirstName(prevState, currentEvent.newFirstName)
                is LastNameChanged -> changeLastName(prevState, currentEvent.newLastName)
                is GenderChanged -> changeGender(prevState, currentEvent.gender)
                is GroupChanged -> changeGroup(prevState, currentEvent.group)
                is LanguageChanged -> changeLanguage(prevState, currentEvent.language)
                is PhoneChanged -> changePhone(prevState, currentEvent.newPhone)
                is ConsentChanged -> changeConsent(prevState, currentEvent.isConsent)
            }
        }
    }

    private fun changeEmail(prevState: RegistrationUiState, newEmail: String): RegistrationUiState {
        // TODO: Добавить валидацию данных
        return prevState.copy(
            email = newEmail
        )
    }

    private fun changePassword(prevState: RegistrationUiState, newPassword: String): RegistrationUiState {
        // TODO: Добавить валидацию данных
        return prevState.copy(
            password = newPassword
        )
    }

    private fun changeRepeatPassword(prevState: RegistrationUiState, newRepeatPassword: String): RegistrationUiState {
        // TODO: Добавить валидацию данных
        return prevState.copy(
            repeatPassword = newRepeatPassword
        )
    }

    private fun changeFirstName(prevState: RegistrationUiState, newFirstName: String): RegistrationUiState {
        // TODO: Добавить валидацию данных
        return prevState.copy(
            firstName = newFirstName
        )
    }

    private fun changeLastName(prevState: RegistrationUiState, newLastName: String): RegistrationUiState {
        // TODO: Добавить валидацию данных
        return prevState.copy(
            lastName = newLastName
        )
    }

    private fun changeGender(prevState: RegistrationUiState, newGender: Gender): RegistrationUiState {
        return prevState.copy(
            gender = newGender
        )
    }

    private fun changeGroup(prevState: RegistrationUiState, newGroup: String): RegistrationUiState {
        return prevState.copy(
            group = newGroup
        )
    }

    private fun changeLanguage(prevState: RegistrationUiState, newLanguage: Language): RegistrationUiState {
        return prevState.copy(
            language = newLanguage
        )
    }

    private fun changePhone(prevState: RegistrationUiState, newPhone: String): RegistrationUiState {
        return if (newPhone.checkLastCharForRegex(numberPhoneRegex))
            prevState.copy(phone = newPhone)
        else prevState
    }

    private fun changeConsent(prevState: RegistrationUiState, isConsent: Boolean): RegistrationUiState {
        return prevState.copy(
            isConsent = isConsent
        )
    }

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