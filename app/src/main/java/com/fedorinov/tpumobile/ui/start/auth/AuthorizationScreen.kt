package com.fedorinov.tpumobile.ui.start.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.fedorinov.tpumobile.R
import com.fedorinov.tpumobile.ui.common.CheckBoxWithText
import com.fedorinov.tpumobile.ui.start.LoginState
import com.fedorinov.tpumobile.ui.start.components.AuthResultWindow
import com.fedorinov.tpumobile.ui.start.components.LogoCompanyBrand
import com.fedorinov.tpumobile.ui.theme.PADDING_BIG
import com.fedorinov.tpumobile.ui.theme.PADDING_MEDIUM
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun AuthorizationScreen() {
    val viewModel: AuthorizationViewModel = getViewModel()
    val uiState by viewModel.uiState.collectAsState()

    AuthorizationScreenStateless(
        // - Состояние окна с результатом авторизации
        loginState = uiState.loginState,
        // - Поле логина
        login = uiState.login,
        onLoginChanged = { newLogin -> },
        // Поле пароля
        password = uiState.password,
        isHidePassword = uiState.isHidePassword,
        onPasswordChanged = { newPassword -> },
        onHideChanged = { },
        // - Войти
        signIn = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AuthorizationScreenStateless(
    // - Состояние окна с результатом авторизации
    loginState: LoginState = LoginState.Loading,
    // - Поле логина
    login: String = "fedorinovea",
    onLoginChanged: (String) -> Unit = {},
    // - Поле пароля
    password: String = "qwerty",
    isHidePassword: Boolean = false,
    onPasswordChanged: (String) -> Unit = {},
    onHideChanged: () -> Unit = {},
    // - Войти
    signIn: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AuthorizationTopBar() },
        content = { paddingValues ->
            AuthorizationContent(
                paddingValues = paddingValues,
                // - Состояние окна с результатом авторизации
                loginState = loginState,
                // - Поле логина
                login = login,
                onLoginChanged = onLoginChanged,
                // - Поле пароля
                password = password,
                isHidePassword = isHidePassword,
                onPasswordChanged = onPasswordChanged,
                onHideChanged = onHideChanged,
                // - Войти
                signIn = signIn,
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AuthorizationTopBar() {
    CenterAlignedTopAppBar(title = { Text(stringResource(R.string.title_authorization)) })
}

@Composable
private fun AuthorizationContent(
    paddingValues: PaddingValues,
    // - Состояние окна с результатом авторизации
    loginState: LoginState = LoginState.Loading,
    // - Поле логина
    login: String = "fedorinovea",
    onLoginChanged: (String) -> Unit = {},
    // Поле пароля
    password: String = "qwerty",
    isHidePassword: Boolean = false,
    onPasswordChanged: (String) -> Unit = {},
    onHideChanged: () -> Unit = {},
    // - Войти
    signIn: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                start = PADDING_BIG,
                end = PADDING_BIG
            )
    ) {
        // - Компонент бренда и логотипа бренда
        LogoCompanyBrand()
        // - Окно с информацией (результатом) авторизации
        AuthResultWindow(
            loginState = loginState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = PADDING_BIG)
        )
        // - Логин
        LoginTextField(
            login = login,
            onLoginChanged = onLoginChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PADDING_BIG)
        )
        // - Пароль
        PasswordTextField(
            password = password,
            isHidePassword = isHidePassword,
            onPasswordChanged = onPasswordChanged,
            onHideChanged = onHideChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PADDING_BIG)
        )
        // - Войти
        Button(
            onClick = signIn,
            enabled = login.isNotEmpty() && password.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PADDING_MEDIUM),
        ) {
            Text(stringResource(R.string.button_sign_in))
        }
        // - Запомнить пользователя + Забыл пароль?
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CheckBoxWithText(
                isChecked = false,
                text = stringResource(R.string.checkbox_remember_me),
                onCheckedChange = {}
            )
            TextButton(onClick = {  }) {
                Text(stringResource(R.string.button_forgot_password))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginTextField(
    modifier: Modifier = Modifier,
    login: String,
    onLoginChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = login,
        onValueChange = onLoginChanged,
        singleLine = true,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PasswordTextField(
    modifier: Modifier = Modifier,
    password: String,
    isHidePassword: Boolean,
    onPasswordChanged: (String) -> Unit,
    onHideChanged: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = password,
        onValueChange = onPasswordChanged,
        singleLine = true,
        visualTransformation =
            if (isHidePassword) PasswordVisualTransformation()
            else VisualTransformation.None,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Key,
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(onClick = onHideChanged) {
                Icon(
                    imageVector =
                        if (isHidePassword) Icons.Outlined.Visibility
                        else Icons.Outlined.VisibilityOff,
                    contentDescription = null
                )
            }
        }
    )
}

@Preview(device = "id:pixel_4", locale = "ru")
@Composable
private fun AuthorizationScreenPreview() {
    TPUMobileTheme {
        AuthorizationScreenStateless()
    }
}