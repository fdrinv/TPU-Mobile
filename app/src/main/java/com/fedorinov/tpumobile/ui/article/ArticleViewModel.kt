package com.fedorinov.tpumobile.ui.article

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorinov.tpumobile.data.repositories.CommonRepository
import com.fedorinov.tpumobile.ui.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// - Сгенерированный ключ библиотекой Compose Destinations, пока что единственный способ достать значение из Bundle,
//   без использования LaunchedEffect
private const val SAVED_STATE_HANDLE_KEY = "userModel"

class ArticleViewModel(
    private val commonRepository: CommonRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState: MutableStateFlow<ArticleUiState> = MutableStateFlow(ArticleUiState())
    val uiState: StateFlow<ArticleUiState> = _uiState

    private val article = requireNotNull(savedStateHandle.get<ArticleModel>(SAVED_STATE_HANDLE_KEY))

    init {
        viewModelScope.launch {
            val article = commonRepository
                .getArticleById(article.id.toString())
                ?.toArticle()

            Log.i(TAG, "article = $article")

            _uiState.update { prevState ->
                prevState.copy(article = article)
            }
        }
    }

    data class ArticleUiState(
        val article: Article? = Article()
    )

    companion object {
        private val TAG = ArticleViewModel::class.simpleName
    }

}