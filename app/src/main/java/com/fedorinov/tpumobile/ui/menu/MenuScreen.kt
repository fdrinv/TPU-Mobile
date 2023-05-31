package com.fedorinov.tpumobile.ui.menu

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.fedorinov.tpumobile.ui.common.scaffold.ScaffoldWithModalDrawer
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun MenuScreen() {

    val viewModel: MenuViewModel = getViewModel()

    val uiState by viewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ScaffoldWithModalDrawer(
        // - Боковое меню
        drawerState = drawerState,
        drawerSelectedItem = uiState.drawerCurrentItem,
        drawerItems = uiState.drawerItems,
        // - Экран
        topBar = {
            MenuTopBar(
                title = "О ТПУ",
                onMenuClicked = {
                    scope.launch { if (drawerState.isOpen) drawerState.close() else drawerState.open() }
                }
            )
        },
        content = { paddingValues ->
            MenuContent(
                paddingValues = paddingValues
           )
        },
        onItemSelected = { newLink -> viewModel.changeDrawerListItem(newLink) }
    )
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
    paddingValues: PaddingValues
) {

}

@Preview
@Composable
private fun MenuScreenPreview() {

}