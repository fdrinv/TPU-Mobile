package com.fedorinov.tpumobile.ui.article

import android.text.Html
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.text.HtmlCompat.fromHtml
import com.fedorinov.tpumobile.data.database.enum.ContentType
import com.fedorinov.tpumobile.ui.common.text.TextHLarge
import com.fedorinov.tpumobile.ui.common.text.TextHMedium
import com.fedorinov.tpumobile.ui.common.text.TextTLarge
import com.fedorinov.tpumobile.ui.common.text.TextTMedium
import com.fedorinov.tpumobile.ui.theme.PADDING_BIG
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.charlex.compose.HtmlText
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun ArticleScreen(
    navigator: DestinationsNavigator,
    userModel: ArticleModel
) {

    val viewModel: ArticleViewModel = getViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        // - Экран
        topBar = {
            ArticleTopBar(
                title = uiState.article?.topic ?: ContentType.ARTICLE.desc,
                onBackClicked = { navigator.popBackStack() }
            )
        },
        content = { paddingValues ->
            ArticleContent(
                paddingValues = paddingValues,

                title = uiState.article?.name,
                text = uiState.article?.text
            )
        },
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArticleTopBar(
    // Заголовок
    title: String,
    // - Нажатие на меню
    onBackClicked: () -> Unit

) {
    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
private fun ArticleContent(
    paddingValues: PaddingValues,

    title: String?,
    text: String?
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(
                top = paddingValues.calculateTopPadding(),
                start = PADDING_BIG,
                end = PADDING_BIG,
                bottom = PADDING_BIG
            )
            .fillMaxSize()
    ) {
        // - Титул статьи
        title?.let {
            TextHMedium(
                text = it,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = PADDING_BIG)
            )
        }
        // - Парсинг HTML тегов
        text?.let {
            val webViewState = rememberWebViewState("")
            WebView(state = )
        }

    }
}