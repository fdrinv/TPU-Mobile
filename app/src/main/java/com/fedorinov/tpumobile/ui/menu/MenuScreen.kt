package com.fedorinov.tpumobile.ui.menu

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun MenuScreen() {
    MenuScreenStateless()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuScreenStateless(
    // Заголовок
    title: String = "О университете",
    // - Нажатие на меню
    onMenuClicked: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            MenuTopBar(
                title = title,
                onMenuClicked = onMenuClicked
            )
        },
        content = { paddingValues ->
            MenuContent(
                paddingValues = paddingValues
            )
        }
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
            // FIXME: Заменить на кнопку
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = null
            )
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