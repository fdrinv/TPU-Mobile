package com.fedorinov.tpumobile.ui.menu

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorinov.tpumobile.data.database.enum.ContentType
import com.fedorinov.tpumobile.data.repositories.AuthRepository
import com.fedorinov.tpumobile.data.repositories.CommonRepository
import com.fedorinov.tpumobile.data.rest.model.response.LinkResponse
import com.fedorinov.tpumobile.ui.model.LinkItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class MenuViewModel(
    private val authRepository: AuthRepository,
    private val commonRepository: CommonRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiState: MutableStateFlow<MenuUiState> = MutableStateFlow(MenuUiState())
    val uiState: StateFlow<MenuUiState> = _uiState
    /*val uiState: StateFlow<MenuUiState> = items
        .map { responseLinks ->
            _uiState.value.copy(
                drawerItems = responseLinks
                    .filter { it.position == 1 }
                    .convertToItems(),
                currentPageItems =
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptySet()
        )*/

    init {
        viewModelScope.launch {
            // - Получаем элементы списка
            val links = commonRepository.getLinks()
            // - Обновляем drawer
            _uiState.update { prevState ->
                val drawerItems = links.map {
                    LinkItem(
                        id = UUID.fromString(it.id),
                        name = it.name,
                        level = it.level,
                        type = ContentType.fromName(it.type),
                        position = it.position,
                        imgUrl = it.image,
                        children = it.children ?: emptyList()
                    )
                }
                prevState.copy(
                    drawerCurrentItem = drawerItems.firstOrNull(),
                    drawerItems = drawerItems
                )
            }
        }
    }

    fun changeDrawerListItem(newLink: LinkItem) {
        _uiState.update { prevState ->
            prevState.copy(drawerCurrentItem = newLink)
        }
    }

    /**
     * Время затраченное на рекурсивный обход дерева составляет ~ 4 ms (на все 68 эле-тов).
     */
    private fun searchLinkItem(links: List<LinkResponse>, searchUUID: UUID): LinkResponse? {
        links.forEach { link ->
            recursiveAdd(link, searchUUID)?.let { return it }
        }

        return null
    }

    /**
     * Рекурсивный обход дерева в ширину.
     */
    private fun recursiveAdd(link: LinkResponse, searchUUID: UUID): LinkResponse? {

        // - Если UUID совпало значит завершаем поиск и возвращаем элемент
        if (UUID.fromString(link.id) == searchUUID) return link

        // - Проверяем есть ли у узла дети
        if (link.children.isNullOrEmpty()) return null

        // - Если есть пробегаемся по каждому дочернему элементу и вызываем эту же функцию
        link.children.forEach { child ->
            recursiveAdd(child, searchUUID)
        }

        return null
    }

    data class MenuUiState(
        val drawerCurrentItem: LinkItem? = null,
        val drawerItems: List<LinkItem> = emptyList(),
        val currentPageItems: List<LinkItem> = emptyList()
    )

    companion object {
        private val TAG = MenuViewModel::class.simpleName
    }

}