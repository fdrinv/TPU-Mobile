package com.fedorinov.tpumobile.ui.start.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fedorinov.tpumobile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTextField(
    modifier: Modifier = Modifier,
    login: String,
    onLoginChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = login,
        onValueChange = onLoginChanged,
        singleLine = true,
        placeholder = {
            Text(stringResource(R.string.text_field_login))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (login.isNotEmpty()) {
                IconButton(onClick = { onLoginChanged("") }) {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = null
                    )
                }
            }
        }
    )
}