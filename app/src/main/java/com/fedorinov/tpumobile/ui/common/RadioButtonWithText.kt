package com.fedorinov.tpumobile.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fedorinov.tpumobile.ui.theme.PADDING_SMALL
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme

@Composable
fun RadioButtonWithText(
    isSelected: Boolean,
    text: String,
    onSelectedChange: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelectedChange
        )
        Spacer(modifier = Modifier.width(PADDING_SMALL))
        Text(text)
    }
}

@Preview
@Composable
private fun RadioButtonWithTextPreview() {
    TPUMobileTheme {
        RadioButtonWithText(true, "Мужской", {})
    }
}
