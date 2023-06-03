package com.fedorinov.tpumobile.ui.menu

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorinov.tpumobile.data.database.enum.ContentType
import com.fedorinov.tpumobile.data.repositories.AuthRepository
import com.fedorinov.tpumobile.data.repositories.CommonRepository
import com.fedorinov.tpumobile.data.rest.model.response.LinkResponse
import com.fedorinov.tpumobile.ui.menu.MenuViewModel.MenuUiState.Error
import com.fedorinov.tpumobile.ui.menu.MenuViewModel.MenuUiState.Loading
import com.fedorinov.tpumobile.ui.menu.MenuViewModel.MenuUiState.Success
import com.fedorinov.tpumobile.ui.model.LinkItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import java.util.Stack
import java.util.UUID

class MenuViewModel(
    private val authRepository: AuthRepository,
    private val commonRepository: CommonRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    // - Навигационный стек для навигации по дереву в рамках одного экрана
    private val navigationPageStack: MutableStateFlow<Stack<LinkItem>> = MutableStateFlow(Stack())

    private val _uiState: MutableStateFlow<MenuUiState> = MutableStateFlow(Loading)
    val uiState: StateFlow<MenuUiState> = _uiState

    init {
        viewModelScope.launch {
            // - Получаем элементы списка
            val links = commonRepository.getLinks()
            // - Загружаем элементы бокового меню
            val drawerItems = links.toListItem()
            // - Открываем первую вкладку меню
            val drawerCurrentItem = drawerItems.firstOrNull()
            // - Загружаем (отображаем) боковое меню
            _uiState.update {
                Success(
                    drawerCurrentItem = drawerCurrentItem,
                    drawerItems = drawerItems
                )
            }
            // - Добавляем текущий элемент в навигационный граф
            navigationPageStack.updateAndGet { prevStack ->
                prevStack.push(drawerCurrentItem)
                prevStack
            }
            (uiState.value as Success?)?.let { state ->
                val newState = state.copy(
                    pageItems = navigationPageStack.value.firstOrNull()?.children.toListItem(),
                    currentPageItem = navigationPageStack.value.first()
                )
                _uiState.update { newState }
            }

        }
    }

    /**
     * Перемещение по боковому меню.
     */
    fun changeDrawerListItem(newLink: LinkItem) {
        val curState = _uiState.value as Success?
        // - Добавляем текущий элемент в навигационный граф
        navigationPageStack.updateAndGet { prevStack ->
            prevStack.clear()
            prevStack.push(newLink)
            prevStack
        }
        _uiState.update {
            curState?.copy(
                drawerCurrentItem = newLink,
                currentPageItem = newLink,
                pageItems = navigationPageStack.value.peek()?.children.toListItem()
            ) ?: Error("Неизвестная ошибка")
        }
    }

    /**
     * Переход на страницу, то бишь вперед по навигационному дереву.
     */
    fun changeCurrentPage(newPage: LinkItem) {
        val curState = _uiState.value as Success?
        // - Добавляем текущий элемент в навигационный граф
        navigationPageStack.updateAndGet { prevStack ->
            prevStack.push(newPage)
            prevStack
        }
        _uiState.update {
            curState?.copy(
                currentPageItem = navigationPageStack.value.peek(),
                pageItems = navigationPageStack.value.peek()?.children.toListItem()
            ) ?: Error("Неизвестная ошибка")
        }
    }

    /**
     * Перемещение назад по навигационному дереву.
     */
    fun onBackClicked() {

        // - Если в навигационном стеке всего лишь один экран или он пустой, то пропускаем данное действие
        if (navigationPageStack.value.empty() || navigationPageStack.value.size == 1) return

        // - Возвращаемся по навигационному графу назад
        navigationPageStack.updateAndGet { prevStack ->
            prevStack.pop()
            prevStack
        }

        // - Обновляем UI
        val curState = _uiState.value as Success?
        _uiState.update {
            curState?.copy(
                currentPageItem = navigationPageStack.value.peek(),
                pageItems = navigationPageStack.value.peek()?.children.toListItem()
            ) ?: Error("Неизвестная ошибка")
        }

    }

    private fun List<LinkResponse>?.toListItem(): List<LinkItem> {
        return if (this.isNullOrEmpty()) emptyList() else this.map {
            LinkItem(
                id = UUID.fromString(it.id),
                name = it.name,
                level = it.level,
                type = ContentType.fromName(it.type),
                position = it.position,
                imgUrl = it.image,
                url = it.url,
                children = it.children ?: emptyList()
            )
        }
    }

    sealed class MenuUiState {
        object Loading : MenuUiState()
        data class Success(
            val drawerCurrentItem: LinkItem? = null,
            val drawerItems: List<LinkItem> = emptyList(),
            val currentPageItem: LinkItem? = null,
            val pageItems: List<LinkItem> = emptyList()
        ) : MenuUiState()
        data class Error(val message: String) : MenuUiState()
    }

    companion object {
        private val TAG = MenuViewModel::class.simpleName
    }

}