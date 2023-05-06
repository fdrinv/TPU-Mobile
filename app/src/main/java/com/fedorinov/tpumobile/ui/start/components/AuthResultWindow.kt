package com.fedorinov.tpumobile.ui.start.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fedorinov.tpumobile.R
import com.fedorinov.tpumobile.data.rest.model.response.AuthResponse
import com.fedorinov.tpumobile.logic.utils.DATE_PATTERN
import com.fedorinov.tpumobile.logic.utils.toString
import com.fedorinov.tpumobile.ui.start.auth.LoginState
import com.fedorinov.tpumobile.ui.start.auth.LoginState.Error
import com.fedorinov.tpumobile.ui.start.auth.LoginState.Loading
import com.fedorinov.tpumobile.ui.start.auth.LoginState.Success
import com.fedorinov.tpumobile.ui.theme.PADDING_BIG
import com.fedorinov.tpumobile.ui.theme.PADDING_MEDIUM
import com.fedorinov.tpumobile.ui.theme.SURFACE_SHAPE
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme
import java.util.Date

@Composable
fun AuthResultWindow(
    loginState: LoginState = Loading,
    modifier: Modifier = Modifier
) {
    when(loginState) {
        is Success -> {
            Surface(
                shape = RoundedCornerShape(SURFACE_SHAPE),
                color = MaterialTheme.colorScheme.primary,
                modifier = modifier
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(PADDING_BIG),
                    text = loginState.text,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        is Error -> {
            Surface(
                shape = RoundedCornerShape(SURFACE_SHAPE),
                color = MaterialTheme.colorScheme.error,
                modifier = modifier
            ) {
                val response = loginState.authResponse
                Column {
                    Text(
                        text = response?.message ?: stringResource(R.string.text_unknown_auth_error),
                        color = MaterialTheme.colorScheme.onError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = PADDING_MEDIUM)
                    )
                    if (response != null) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = response.type,
                                color = MaterialTheme.colorScheme.onError
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = response.date,
                                color = MaterialTheme.colorScheme.onError
                            )
                        }
                    }
                }
            }
        }
        is Loading -> {
            CircularProgressIndicator(modifier = modifier, color = MaterialTheme.colorScheme.primary)
        }
        else -> Box(modifier = Modifier.padding(PADDING_MEDIUM)) { }
    }
}

@Preview
@Composable
private fun AuthResultWindowPreview() {
    TPUMobileTheme {
        // AuthResultWindow(Success(text = "Все прошло успешно, сервер вернул отличный ответ!"))
        AuthResultWindow(
            Error(
                authResponse = AuthResponse(
                    type = "InterruptedIOException: timeout",
                    date = Date().toString(DATE_PATTERN),
                    message = stringResource(R.string.exception_timeout)
                )
            )
        )
    }
}