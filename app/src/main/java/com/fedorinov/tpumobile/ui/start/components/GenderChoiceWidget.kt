package com.fedorinov.tpumobile.ui.start.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fedorinov.tpumobile.R
import com.fedorinov.tpumobile.ui.start.reg.Gender
import com.fedorinov.tpumobile.ui.theme.PADDING_MEDIUM

@Composable
fun GenderChoiceWidget(
    modifier: Modifier = Modifier,
    selected: Gender,
    onGenderClicked: (Gender) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Row {
            // - Мужской
            RadioButton(
                selected = selected == Gender.MALE,
                onClick = { onGenderClicked(Gender.MALE) }
            )
            Spacer(modifier = Modifier.width(PADDING_MEDIUM))
            Text(stringResource(R.string.male))
        }
        Row {
            // - Женский
            RadioButton(
                selected = selected == Gender.FEMALE,
                onClick = { onGenderClicked(Gender.FEMALE) }
            )
            Spacer(modifier = Modifier.width(PADDING_MEDIUM))
            Text(stringResource(R.string.female))
        }
    }
}

@Preview
@Composable
private fun GenderChoiceWidgetPreview() {
    GenderChoiceWidget(Gender.MALE, {})
}