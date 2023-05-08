package com.fedorinov.tpumobile.ui.start.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.fedorinov.tpumobile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    password: String,
    placeholderText: String,
    onPasswordChanged: (String) -> Unit
) {
    var isHidePassword by rememberSaveable { mutableStateOf(true) }
    OutlinedTextField(
        modifier = modifier,
        value = password,
        onValueChange = onPasswordChanged,
        singleLine = true,
        visualTransformation =
        if (isHidePassword) PasswordVisualTransformation()
        else VisualTransformation.None,
        placeholder = {
            Text(placeholderText)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Key,
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(onClick = { isHidePassword = !isHidePassword }) {
                Icon(
                    imageVector =
                    if (isHidePassword) Icons.Outlined.VisibilityOff
                    else Icons.Outlined.Visibility,
                    contentDescription = null,
                    tint =
                    if (isHidePassword) Color.Unspecified
                    else MaterialTheme.colorScheme.primary,
                )
            }
        }
    )
}