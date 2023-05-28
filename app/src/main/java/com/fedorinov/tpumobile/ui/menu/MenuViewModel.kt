package com.fedorinov.tpumobile.ui.menu

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorinov.tpumobile.data.repositories.AuthRepository
import com.fedorinov.tpumobile.data.rest.RestApiTpu
import com.fedorinov.tpumobile.data.rest.model.response.LinkResponse
import com.fedorinov.tpumobile.logic.service.LinkLoaderService
import com.fedorinov.tpumobile.ui.model.LinkItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MenuViewModel(
    private val authRepository: AuthRepository,
    private val restApi: RestApiTpu,
    private val linkLoaderService: LinkLoaderService,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val items: StateFlow<Set<LinkResponse>> = linkLoaderService.links

    private val _uiState: MutableStateFlow<MenuUiState> = MutableStateFlow(MenuUiState())
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
            linkLoaderService.loadAllMap()
        }
    }

    private fun List<LinkResponse>.convertToItems(): List<LinkItem> =
        this.map { it.toLinkItem() }


    data class MenuUiState(
        val drawerItems: List<LinkItem> = emptyList(),
        val currentPageItems: List<LinkItem> = emptyList()
    )

    companion object {
        private val TAG = MenuViewModel::class.simpleName
    }

}