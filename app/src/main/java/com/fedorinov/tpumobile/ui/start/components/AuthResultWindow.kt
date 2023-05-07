package com.fedorinov.tpumobile.ui.start.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.fedorinov.tpumobile.R
import com.fedorinov.tpumobile.data.rest.model.response.AuthResponse
import com.fedorinov.tpumobile.logic.utils.DATE_PATTERN
import com.fedorinov.tpumobile.logic.utils.toString
import com.fedorinov.tpumobile.ui.common.text.TextBMedium
import com.fedorinov.tpumobile.ui.common.text.TextTMedium
import com.fedorinov.tpumobile.ui.start.auth.AuthState
import com.fedorinov.tpumobile.ui.start.auth.AuthState.Error
import com.fedorinov.tpumobile.ui.start.auth.AuthState.Loading
import com.fedorinov.tpumobile.ui.start.auth.AuthState.Success
import com.fedorinov.tpumobile.ui.theme.ICON_MEDIUM_SIZE
import com.fedorinov.tpumobile.ui.theme.PADDING_BIG
import com.fedorinov.tpumobile.ui.theme.PADDING_MEDIUM
import com.fedorinov.tpumobile.ui.theme.PADDING_SMALL
import com.fedorinov.tpumobile.ui.theme.SURFACE_SHAPE
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme
import java.util.Date

@Composable
fun AuthResultWindow(
    modifier: Modifier = Modifier,
    authState: AuthState
) {
    when(authState) {
        is Success, is Error -> {
            Surface(
                shape = RoundedCornerShape(SURFACE_SHAPE),
                color =
                    if (authState is Success) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.error,
                modifier = modifier
            ) {
                if (authState is Success) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(PADDING_BIG),
                        text = stringResource(R.string.text_success_auth),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    val response = (authState as Error).authResponse
                    Column(
                        modifier = Modifier
                            .width(IntrinsicSize.Max)
                            .padding(PADDING_MEDIUM)
                    ) {
                        // - Описание ошибки
                        TextTMedium(
                            text = "${stringResource(R.string.text_error_desc)}:",
                            color = MaterialTheme.colorScheme.onError
                        )
                        TextBMedium(
                            text = response?.message
                                ?: stringResource(R.string.text_unknown_auth_error),
                            color = MaterialTheme.colorScheme.onError,
                            modifier = Modifier
                                .padding(bottom = PADDING_MEDIUM)
                        )
                        if (response != null) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                // - Тип ошибки
                                Column(modifier = Modifier.weight(1f)) {
                                    TextTMedium(
                                        text = "${stringResource(R.string.text_error_type)}:",
                                        color = MaterialTheme.colorScheme.onError,
                                    )
                                    TextBMedium(
                                        text = response.type,
                                        color = MaterialTheme.colorScheme.onError,
                                    )
                                }
                                // - Дата ошибки
                                Box(modifier = Modifier.weight(1f)) {
                                    Column(
                                        horizontalAlignment = Alignment.Start,
                                        modifier = Modifier.align(Alignment.CenterEnd)
                                    ) {
                                        TextTMedium(
                                            text = "${stringResource(R.string.text_time)}:",
                                            color = MaterialTheme.colorScheme.onError,
                                        )
                                        TextBMedium(
                                            text = response.date,
                                            color = MaterialTheme.colorScheme.onError,
                                            textAlign = TextAlign.Center,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        is Loading -> {
            CircularProgressIndicator(modifier = Modifier.size(ICON_MEDIUM_SIZE), color = MaterialTheme.colorScheme.primary)
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
            authState = Error(
                authResponse = AuthResponse(
                    type = "InterruptedIOException: timeout",
                    date = Date().toString(DATE_PATTERN),
                    message = stringResource(R.string.exception_timeout)
                )
            )
        )
    }
}