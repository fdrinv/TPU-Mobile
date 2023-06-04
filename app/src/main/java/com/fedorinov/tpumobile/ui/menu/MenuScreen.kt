package com.fedorinov.tpumobile.ui.menu

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.fedorinov.tpumobile.data.database.enum.ContentType.*
import com.fedorinov.tpumobile.ui.article.ArticleModel
import com.fedorinov.tpumobile.ui.common.menu.MainMenuItem
import com.fedorinov.tpumobile.ui.common.scaffold.ScaffoldWithModalDrawer
import com.fedorinov.tpumobile.ui.destinations.ArticleScreenDestination
import com.fedorinov.tpumobile.ui.menu.MenuViewModel.MenuUiState.*
import com.fedorinov.tpumobile.ui.model.LinkItem
import com.fedorinov.tpumobile.ui.theme.PADDING_BIG
import com.fedorinov.tpumobile.ui.theme.PADDING_MEDIUM
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun MenuScreen(navigator: DestinationsNavigator) {

    val viewModel: MenuViewModel = getViewModel()

    val uiState by viewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // - Обработчик нажатия назад
    BackHandler(true) { viewModel.onBackClicked() }

    // - Если перешли в расписание или по внешней ссылки, то закрываем drawer
    LaunchedEffect(uiState) {
        when(val state = uiState) {
            is Success -> {
                if (state.currentPageItem?.type == LINK || state.currentPageItem?.type == SCHEDULE) {
                    drawerState.close()
                }
            }
            else -> {

            }
        }
    }

    when (val state = uiState) {
        is Success ->
            ScaffoldWithModalDrawer(
                // - Боковое меню
                drawerState = drawerState,
                drawerSelectedItem = state.drawerCurrentItem,
                drawerItems = state.drawerItems,
                gesturesEnabled = state.currentPageItem?.type == LINKS_LIST || state.currentPageItem?.type == ARTICLE,
                // - Экран
                topBar = {
                    MenuTopBar(
                        title = state.drawerCurrentItem?.name ?: "",
                        onMenuClicked = {
                            scope.launch { if (drawerState.isOpen) drawerState.close() else drawerState.open() }
                        }
                    )
                },
                content = { paddingValues ->
                    MenuContent(
                        paddingValues = paddingValues,
                        currentPageItem = state.currentPageItem,
                        pageItems = state.pageItems,
                        onItemClicked = { newPage ->
                            if (newPage.type != ARTICLE) viewModel.changeCurrentPage(newPage)
                            else {
                                newPage.articleId?.let { articleId ->
                                    navigator.navigate(
                                        ArticleScreenDestination(
                                            ArticleModel(id = articleId)
                                        )
                                    )
                                }
                            }
                        }
                    )
                },
                onItemSelected = { newLink -> viewModel.changeDrawerListItem(newLink) }
            )
        is Loading -> {
            CircularProgressIndicator()
        }
        is Error -> {
            Icon(
                imageVector = Icons.Filled.Error,
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuTopBar(
    // Заголовок
    title: String,
    // - Нажатие на меню
    onMenuClicked: () -> Unit

) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onMenuClicked) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
private fun MenuContent(
    paddingValues: PaddingValues,

    pageItems: List<LinkItem>,
    currentPageItem: LinkItem?,

    onItemClicked: (LinkItem) -> Unit
) {
    Log.i("MenuViewModel", "currentPageItem = ${currentPageItem}}")

    when (currentPageItem?.type) {
        LINKS_LIST -> {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(top = paddingValues.calculateTopPadding())
                    .fillMaxSize()
            ) {
                pageItems.forEach { link ->
                    MainMenuItem(
                        modifier = Modifier
                            .clickable { onItemClicked(link) }
                            .padding(
                                start = PADDING_BIG,
                                end = PADDING_BIG,
                                bottom = PADDING_MEDIUM
                            )
                            .fillMaxWidth(),
                        title = link.name,
                        image = link.imgUrl ?: "",
                        contentType = link.type ?: LINK
                    )
                    Divider(
                        thickness = Dp.Hairline,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
        }

        ARTICLE -> {

        }

        LINK, SCHEDULE -> {
            currentPageItem.url?.let { url ->
                val webViewState = rememberWebViewState(url)
                WebView(state = webViewState)
            }
        }

        else -> {}
    }
}