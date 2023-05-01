package com.fedorinov.tpumobile.ui.common

import android.widget.CheckBox
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fedorinov.tpumobile.ui.theme.PADDING_MEDIUM
import com.fedorinov.tpumobile.ui.theme.PADDING_SMALL
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme

@Composable
fun CheckBoxWithText(
    isChecked: Boolean,
    text: String,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
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

@Preview
@Composable
private fun CheckBoxWithTextPreview() {
    TPUMobileTheme {
        CheckBoxWithText(false, "Запомнить меня?", {})
    }
}