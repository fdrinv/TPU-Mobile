package com.fedorinov.tpumobile.ui.common.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fedorinov.tpumobile.ui.common.text.TextBLarge
import com.fedorinov.tpumobile.ui.theme.PADDING_BIG
import com.fedorinov.tpumobile.ui.theme.PADDING_MEDIUM
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme

@Composable
fun DefaultListItem(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextBLarge(
            modifier = Modifier.padding(horizontal = PADDING_BIG, vertical = PADDING_MEDIUM),
            text = text
        )
    }
}

@Preview
@Composable
private fun DefaultListItemPreview() {
    TPUMobileTheme {
        Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            DefaultListItem("8К91", Modifier.fillMaxWidth(), {})
            DefaultListItem("8К93", Modifier.fillMaxWidth(), {})
            DefaultListItem("8В21", Modifier.fillMaxWidth(), {})
            DefaultListItem("8Д105", Modifier.fillMaxWidth(), {})
            DefaultListItem("2УК32", Modifier.fillMaxWidth(), {})
        }
    }
}