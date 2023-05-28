package com.fedorinov.tpumobile.ui.common.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.ReadMore
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.fedorinov.tpumobile.data.database.enum.ContentType
import com.fedorinov.tpumobile.data.database.enum.ContentType.*
import com.fedorinov.tpumobile.ui.common.text.TextBLarge
import com.fedorinov.tpumobile.ui.theme.LIST_ITEM_SIZE
import com.fedorinov.tpumobile.ui.theme.PADDING_BIG
import com.fedorinov.tpumobile.ui.theme.PADDING_MEDIUM
import com.fedorinov.tpumobile.ui.theme.SURFACE_SHAPE
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme

@Composable
fun MainMenuItem(
    modifier: Modifier = Modifier,
    isPreview: Boolean = true,
    title: String,
    image: String,
    contentType: ContentType
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(SURFACE_SHAPE)
    ) {
        Row(
            modifier = Modifier
                .padding(PADDING_MEDIUM)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            // - Фотографии
            if (!isPreview) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = PADDING_MEDIUM)
                        .height(LIST_ITEM_SIZE)
                        .width(LIST_ITEM_SIZE)
                )
            } else {
                Box(
                    modifier = Modifier
                        .padding(end = PADDING_MEDIUM)
                        .background(Color.Red)
                        .height(LIST_ITEM_SIZE)
                        .width(LIST_ITEM_SIZE)
                )
            }
            // - Титул кнопки
            TextBLarge(
                text = title,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(end = PADDING_MEDIUM)
                    .weight(1f)
            )
            // - Иконка индикатор
            Icon(
                modifier = Modifier.align(Alignment.CenterVertically),
                imageVector = when(contentType) {
                    LINKS_LIST -> Icons.Filled.ReadMore
                    ARTICLE -> Icons.Filled.Article
                    LINK -> Icons.Filled.Link
                    SCHEDULE -> Icons.Filled.Schedule
                },
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun MainMenuItemPreview() {
    TPUMobileTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .sizeIn(minHeight = LIST_ITEM_SIZE)
                .padding(horizontal = PADDING_MEDIUM)
        ) {
            MainMenuItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Томский политехнический университет",
                image = "https://internationals.tpu.ru:8080/api/media/img/0EF1D11C-0A77-4A96-B009-1956D385D8ED",
                contentType = ARTICLE,
            )
        }
    }
}