package com.fedorinov.tpumobile.ui.common.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fedorinov.tpumobile.ui.common.text.TextLLarge
import com.fedorinov.tpumobile.ui.model.LinkItem
import com.fedorinov.tpumobile.ui.theme.ICON_SMALL_SIZE
import java.util.UUID

private val drawerTonalElevation = 4.dp
private val drawerShapeCornerSmall = 8.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithModalDrawer(
    modifier: Modifier = Modifier,
    drawerSelectedItem: LinkItem? = null,
    drawerState: DrawerState,
    drawerItems: List<LinkItem>,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    onItemSelected: (LinkItem) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface,
                drawerContentColor = MaterialTheme.colorScheme.onSurface,
                drawerTonalElevation = drawerTonalElevation,
                drawerShape = RoundedCornerShape(
                    topEnd = drawerShapeCornerSmall,
                    bottomEnd = drawerShapeCornerSmall
                )
            ) {
                drawerItems.forEach { item ->
                    NavigationDrawerItem(
                        label = {
                            TextLLarge(item.name)
                        },
                        icon = {
                            AsyncImage(
                                model = item.imgUrl,
                                contentDescription = null,
                                modifier = Modifier.size(ICON_SMALL_SIZE)
                            )
                        },
                        selected = item.id == drawerSelectedItem?.id,
                        onClick = { onItemSelected(item) }
                    )
                }
            }
        }
    ) {
        Scaffold(
            modifier = modifier,
            topBar = topBar,
            bottomBar = bottomBar,
            snackbarHost = snackbarHost,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            containerColor = containerColor,
            contentColor = contentColor,
            contentWindowInsets = contentWindowInsets,
            content = content
        )
    }
}
