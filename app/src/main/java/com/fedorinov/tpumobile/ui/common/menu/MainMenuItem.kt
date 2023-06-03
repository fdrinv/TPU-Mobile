package com.fedorinov.tpumobile.ui.common.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.fedorinov.tpumobile.data.database.enum.ContentType
import com.fedorinov.tpumobile.data.database.enum.ContentType.ARTICLE
import com.fedorinov.tpumobile.data.database.enum.ContentType.LINK
import com.fedorinov.tpumobile.data.database.enum.ContentType.LINKS_LIST
import com.fedorinov.tpumobile.data.database.enum.ContentType.SCHEDULE
import com.fedorinov.tpumobile.ui.common.text.TextBLarge
import com.fedorinov.tpumobile.ui.common.text.TextBMedium
import com.fedorinov.tpumobile.ui.common.text.TextBSmall
import com.fedorinov.tpumobile.ui.common.text.TextTLarge
import com.fedorinov.tpumobile.ui.common.text.TextTMedium
import com.fedorinov.tpumobile.ui.theme.IMAGE_MENU_SIZE
import com.fedorinov.tpumobile.ui.theme.LINE_HEIGHT_TEXT
import com.fedorinov.tpumobile.ui.theme.PADDING_MEDIUM
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme

@Composable
fun MainMenuItem(
    modifier: Modifier = Modifier,
    title: String,
    image: String,
    contentType: ContentType
) {
    // - Описание ссылки
    val contentTypeText = remember(contentType) {
        buildString {
            when (contentType) {
                LINKS_LIST -> append(contentType.desc)
                ARTICLE -> append(contentType.desc)
                LINK -> append(contentType.desc)
                SCHEDULE -> append(contentType.desc)
            }
        }
    }

    // - Иконка перехода
    val trailingIcon = remember(contentType) {
        when (contentType) {
            LINKS_LIST, ARTICLE -> Icons.Filled.ChevronRight
            LINK -> Icons.Filled.Link
            SCHEDULE -> Icons.Filled.Schedule
        }
    }

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(PADDING_MEDIUM)
                .fillMaxWidth(),
        ) {
            // - Фотографии
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = PADDING_MEDIUM)
                    .size(IMAGE_MENU_SIZE)
            )
            Column(
                modifier = Modifier
                    .padding(end = PADDING_MEDIUM)
                    .weight(1f)
            ) {
                // - Титул кнопки
                TextTMedium(
                    text = title,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    lineHeight = LINE_HEIGHT_TEXT,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(end = PADDING_MEDIUM)
                )
                // - Описание перехода по кнопке
                TextBMedium(
                    text = contentTypeText,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(end = PADDING_MEDIUM)
                )
            }
            Icon(
                imageVector = trailingIcon,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun MainMenuItemPreview() {
    TPUMobileTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            Column(
                modifier = Modifier
                    .padding(PADDING_MEDIUM)
                    .fillMaxSize()
            ) {
                MainMenuItem(
                    modifier = Modifier
                        .padding(bottom = PADDING_MEDIUM)
                        .fillMaxWidth(),
                    title = "Томский политехнический университет",
                    image = "https://internationals.tpu.ru:8080/api/media/img/0EF1D11C-0A77-4A96-B009-1956D385D8ED",
                    contentType = ARTICLE,
                )
                Divider(
                    thickness = Dp.Hairline,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                MainMenuItem(
                    modifier = Modifier
                        .padding(bottom = PADDING_MEDIUM)
                        .fillMaxWidth(),
                    title = "Почему ТПУ?",
                    image = "https://internationals.tpu.ru:8080/api/media/img/0EF1D11C-0A77-4A96-B009-1956D385D8ED",
                    contentType = ARTICLE,
                )
                Divider(
                    thickness = Dp.Hairline,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                MainMenuItem(
                    modifier = Modifier
                        .padding(bottom = PADDING_MEDIUM)
                        .fillMaxWidth(),
                    title = "Обучение на русском языке",
                    image = "https://internationals.tpu.ru:8080/api/media/img/0EF1D11C-0A77-4A96-B009-1956D385D8ED",
                    contentType = ARTICLE,
                )
                Divider(
                    thickness = Dp.Hairline,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                MainMenuItem(
                    modifier = Modifier
                        .padding(bottom = PADDING_MEDIUM)
                        .fillMaxWidth(),
                    title = "Программы",
                    image = "https://internationals.tpu.ru:8080/api/media/img/0EF1D11C-0A77-4A96-B009-1956D385D8ED",
                    contentType = LINKS_LIST,
                )
                Divider(
                    thickness = Dp.Hairline,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                MainMenuItem(
                    modifier = Modifier
                        .padding(bottom = PADDING_MEDIUM)
                        .fillMaxWidth(),
                    title = "Программы",
                    image = "https://internationals.tpu.ru:8080/api/media/img/0EF1D11C-0A77-4A96-B009-1956D385D8ED",
                    contentType = LINKS_LIST,
                )
                Divider(
                    thickness = Dp.Hairline,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                MainMenuItem(
                    modifier = Modifier
                        .padding(bottom = PADDING_MEDIUM)
                        .fillMaxWidth(),
                    title = "Презентационные материалы о ТПУ",
                    image = "https://internationals.tpu.ru:8080/api/media/img/0EF1D11C-0A77-4A96-B009-1956D385D8ED",
                    contentType = LINK,
                )
                Divider(
                    thickness = Dp.Hairline,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        }
    }
}