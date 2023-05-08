package com.fedorinov.tpumobile.ui.start.reg

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fedorinov.tpumobile.R
import com.fedorinov.tpumobile.ui.common.RadioButtonWithText
import com.fedorinov.tpumobile.ui.start.components.LogoCompanyBrand
import com.fedorinov.tpumobile.ui.start.components.PasswordTextField
import com.fedorinov.tpumobile.ui.theme.PADDING_BIG
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme

@Composable
fun RegistrationScreen(onBackClicked: () -> Unit) {
    RegistrationScreenStateless(

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegistrationScreenStateless(
    // - Поля авторизации
    email: String = "fedorinovea@indorsoft.ru",
    onEmailChanged: (String) -> Unit = {},
    password: String = "qwerty",
    onPasswordChanged: (String) -> Unit = {},
    repeatPassword: String = "qwerty",
    onRepeatPasswordChanged: (String) -> Unit = {},
    firstName: String = "Евгений",
    onFirstNameChanged: (String) -> Unit = {},
    lastName: String = "Федоринов",
    onLastNameChanged: (String) -> Unit = {},
    gender: Gender = Gender.MALE,
    onGenderChanged: (Gender) -> Unit = {},
    group: String = "8К91",
    onGroupChanged: (String) -> Unit = {},
    language: Language = Language.RUSSIAN,
    onLanguageChanged: (Language) -> Unit = {},
    phone: String = "89138754824",
    onPhoneChanged: (String) -> Unit = {},
    isConsent: Boolean = true,
    onConsentChanged: (Boolean) -> Unit = {},
    // - Создание аккаунта
    onCreateAccountClicked: () -> Unit = {},
    // - Уже есть аккаунт
    onHaveAccountClicked: () -> Unit = {},
    // - Назад
    onBackClicked: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { RegistrationTopBar(onBackClicked = onBackClicked) },
        content = { paddingValues ->
            RegistrationContent(
                paddingValues = paddingValues,
                // - Поля авторизации
                email = email,
                onEmailChanged = onEmailChanged,
                password = password,
                onPasswordChanged = onPasswordChanged,
                repeatPassword = repeatPassword,
                onRepeatPasswordChanged = onRepeatPasswordChanged,
                firstName = firstName,
                onFirstNameChanged = onFirstNameChanged,
                lastName = lastName,
                onLastNameChanged = onLastNameChanged,
                gender = gender,
                onGenderChanged = onGenderChanged,
                group = group,
                onGroupChanged = onGroupChanged,
                language = language,
                onLanguageChanged = onLanguageChanged,
                phone = phone,
                onPhoneChanged = onPhoneChanged,
                isConsent = isConsent,
                onConsentChanged = onConsentChanged,
                // - Создание аккаунта
                onCreateAccountClicked = onCreateAccountClicked,
                // - Уже есть аккаунт
                onHaveAccountClicked = onHaveAccountClicked,
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegistrationTopBar(onBackClicked: () -> Unit) {
    CenterAlignedTopAppBar(title = { Text(stringResource(R.string.title_registration)) })
}

@Composable
private fun RegistrationContent(
    paddingValues: PaddingValues,
    // - Поля авторизации
    email: String,
    onEmailChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    repeatPassword: String,
    onRepeatPasswordChanged: (String) -> Unit,
    firstName: String,
    onFirstNameChanged: (String) -> Unit,
    lastName: String,
    onLastNameChanged: (String) -> Unit,
    gender: Gender,
    onGenderChanged: (Gender) -> Unit,
    group: String,
    onGroupChanged: (String) -> Unit,
    language: Language,
    onLanguageChanged: (Language) -> Unit,
    phone: String,
    onPhoneChanged: (String) -> Unit,
    isConsent: Boolean,
    onConsentChanged: (Boolean) -> Unit,
    // - Создание аккаунта
    onCreateAccountClicked: () -> Unit,
    // - Уже есть аккаунт
    onHaveAccountClicked: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                start = PADDING_BIG,
                end = PADDING_BIG
            )
            .verticalScroll(rememberScrollState())
    ) {
        // - Компонент бренда и логотипа бренда
        LogoCompanyBrand(modifier = Modifier.padding(bottom = PADDING_BIG))
        // - Электронная почта
        EmailTextField(
            email = email,
            onEmailChanged = onEmailChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PADDING_BIG)
        )
        // - Пароль
        PasswordTextField(
            password = password,
            placeholderText = stringResource(R.string.text_field_password),
            onPasswordChanged = onPasswordChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PADDING_BIG)
        )
        // - Повторный пароль
        PasswordTextField(
            password = repeatPassword,
            placeholderText = stringResource(R.string.text_field_repeat_password),
            onPasswordChanged = onRepeatPasswordChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PADDING_BIG)
        )
        // - Имя пользователя
        FirstNameTextField(
            firstName = firstName,
            onFirstNameChanged = onFirstNameChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PADDING_BIG)
        )
        // - Имя пользователя
        LastNameTextField(
            lastName = lastName,
            onLastNameChanged = onLastNameChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PADDING_BIG)
        )
        // - Гендер пользователя
        GenderRadioButtons(
            gender = gender,
            onGenderChanged = onGenderChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PADDING_BIG)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EmailTextField(
    modifier: Modifier = Modifier,
    email: String,
    onEmailChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = email,
        onValueChange = onEmailChanged,
        singleLine = true,
        placeholder = {
            Text(stringResource(R.string.text_field_email))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Email,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (email.isNotEmpty()) {
                IconButton(onClick = { onEmailChanged("") }) {
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
private fun FirstNameTextField(
    modifier: Modifier = Modifier,
    firstName: String,
    onFirstNameChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = firstName,
        onValueChange = onFirstNameChanged,
        singleLine = true,
        placeholder = {
            Text(stringResource(R.string.text_field_first_name))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (firstName.isNotEmpty()) {
                IconButton(onClick = { onFirstNameChanged("") }) {
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
private fun LastNameTextField(
    modifier: Modifier = Modifier,
    lastName: String,
    onLastNameChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = lastName,
        onValueChange = onLastNameChanged,
        singleLine = true,
        placeholder = {
            Text(stringResource(R.string.text_field_last_name))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (lastName.isNotEmpty()) {
                IconButton(onClick = { onLastNameChanged("") }) {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Composable
private fun GenderRadioButtons(
    modifier: Modifier = Modifier,
    gender: Gender,
    onGenderChanged: (Gender) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // - Мужской
        RadioButtonWithText(
            text = stringResource(R.string.male),
            isSelected = gender == Gender.MALE,
            onSelectedChange = { onGenderChanged(Gender.MALE) }
        )
        // - Женский
        RadioButtonWithText(
            text = stringResource(R.string.female),
            isSelected = gender == Gender.FEMALE,
            onSelectedChange = { onGenderChanged(Gender.FEMALE) }
        )
    }
}

@Preview
@Composable
private fun RegistrationScreenPreview() {
    TPUMobileTheme {
        RegistrationScreenStateless()
    }
}