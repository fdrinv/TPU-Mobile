package com.fedorinov.tpumobile.ui.start.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fedorinov.tpumobile.ui.start.LoginState
import com.fedorinov.tpumobile.ui.start.LoginState.Error
import com.fedorinov.tpumobile.ui.start.LoginState.Loading
import com.fedorinov.tpumobile.ui.start.LoginState.Success
import com.fedorinov.tpumobile.ui.theme.PADDING_BIG
import com.fedorinov.tpumobile.ui.theme.SURFACE_SHAPE
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme

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
                    modifier = Modifier.fillMaxWidth().padding(PADDING_BIG),
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
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = loginState.text,
                    color = MaterialTheme.colorScheme.onError
                )
            }
        }
        is Loading -> {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        else -> Box { }
    }
}

@Preview
@Composable
private fun AuthResultWindowPreview() {
    TPUMobileTheme {
        AuthResultWindow(Success(text = "Все прошло успешно, сервер вернул отличный ответ!"))
    }
}