package com.fedorinov.tpumobile.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import com.fedorinov.tpumobile.ui.theme.PADDING_SMALL
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme

@Composable
fun CheckBoxWithText(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    text: String,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
        Spacer(modifier = Modifier.width(PADDING_SMALL))
        Text(text)
    }
}

@Composable
fun CheckBoxWithText(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    text: AnnotatedString,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
        Spacer(modifier = Modifier.width(PADDING_SMALL))
        Text(text)
    }
}

@Composable
fun CheckBoxWithText(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    text: @Composable () -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
        Spacer(modifier = Modifier.width(PADDING_SMALL))
        text()
    }
}

@Preview
@Composable
private fun CheckBoxWithTextPreview() {
    TPUMobileTheme {
        CheckBoxWithText(Modifier, false, "Запомнить меня?", {})
    }
}