package com.fedorinov.tpumobile.ui.start.reg

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fedorinov.tpumobile.R
import com.fedorinov.tpumobile.data.database.entity.GroupEntity
import com.fedorinov.tpumobile.ui.common.CheckBoxWithText
import com.fedorinov.tpumobile.ui.common.RadioButtonWithText
import com.fedorinov.tpumobile.ui.common.icons.BackAction
import com.fedorinov.tpumobile.ui.common.list.DefaultListItem
import com.fedorinov.tpumobile.ui.common.list.EmptyList
import com.fedorinov.tpumobile.ui.common.menu.DefaultMenu
import com.fedorinov.tpumobile.ui.common.text.TextBMedium
import com.fedorinov.tpumobile.ui.start.components.LogoCompanyBrand
import com.fedorinov.tpumobile.ui.start.components.PasswordTextField
import com.fedorinov.tpumobile.ui.start.reg.RegistrationUiEvent.*
import com.fedorinov.tpumobile.ui.theme.BUTTON_SHAPE
import com.fedorinov.tpumobile.ui.theme.PADDING_BIG
import com.fedorinov.tpumobile.ui.theme.PADDING_MEDIUM
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

private val LIST_GROUPS_MAX_HEIGHT = 150.dp

@Destination
@Composable
fun RegistrationScreen(navigator: DestinationsNavigator) {
    val viewModel: RegistrationViewModel = getViewModel()
    val uiState by viewModel.uiState.collectAsState()

    RegistrationScreenStateless(
        // - Поля авторизации
        email = uiState.email,
        onEmailChanged = { newEmail -> viewModel.receiveUiEvent(EmailChanged(newEmail)) },
        password = uiState.password,
        onPasswordChanged = { newPassword -> viewModel.receiveUiEvent(PasswordChanged(newPassword)) },
        repeatPassword = uiState.repeatPassword,
        onRepeatPasswordChanged = { newRepeatPassword -> viewModel.receiveUiEvent(RepeatPasswordChanged(newRepeatPassword)) },
        firstName = uiState.firstName,
        onFirstNameChanged = { newFirstName -> viewModel.receiveUiEvent(FirstNameChanged(newFirstName))},
        lastName = uiState.lastName,
        onLastNameChanged = { newLastName -> viewModel.receiveUiEvent(LastNameChanged(newLastName))},
        gender = uiState.gender,
        onGenderChanged = { gender -> viewModel.receiveUiEvent(GenderChanged(gender))},
        group = uiState.group,
        groups = uiState.groups,
        onGroupChanged = { newGroup -> viewModel.receiveUiEvent(GroupChanged(newGroup))},
        language = uiState.language,
        onLanguageChanged = { language -> viewModel.receiveUiEvent(LanguageChanged(language)) },
        phone = uiState.phone,
        onPhoneChanged = { newPhone -> viewModel.receiveUiEvent(PhoneChanged(newPhone)) },
        isConsent = uiState.isConsent,
        onConsentChanged = { isConsent -> viewModel.receiveUiEvent(ConsentChanged(isConsent)) },
        // - Создание аккаунта
        onCreateAccountClicked = {},
        // - Уже есть аккаунт
        onHaveAccountClicked = { navigator.popBackStack() },
        // - Назад
        onBackClicked = { navigator.popBackStack() }
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
    groups: List<GroupEntity> = emptyList(),
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
                groups = groups,
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
    CenterAlignedTopAppBar(
        navigationIcon = { BackAction(onClick = onBackClicked) },
        title = { Text(stringResource(R.string.title_registration)) }
    )
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
    groups: List<GroupEntity>,
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
        // - Выбор группы
        GroupTextField(
            group = group,
            groups = groups,
            onGroupChanged = onGroupChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PADDING_BIG)
        )
        // - Выбор языка
        LanguageTextField(
            language = language,
            onLanguageChanged = onLanguageChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PADDING_BIG)
        )
        // - Номер телефона
        PhoneTextField(
            phone = phone,
            onPhoneChanged = onPhoneChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PADDING_BIG)
        )
        // - Согласие на обработку перс. данных
        CheckBoxWithText(
            isChecked = isConsent,
            text = {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.text_consent))
                        append(" ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline)) {
                            append(stringResource(R.string.text_personal_data))
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onCheckedChange = onConsentChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PADDING_MEDIUM),
        )
        // - Создать аккаунт
        Button(
            onClick = onCreateAccountClicked,
            enabled = true,
            shape = RoundedCornerShape(BUTTON_SHAPE),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.button_sign_up))
        }
        // - Аккаунт уже имеется
        TextButton(
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurfaceVariant),
            onClick = onHaveAccountClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PADDING_MEDIUM)
        ) {
            Text(stringResource(R.string.button_already_account))
        }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GroupTextField(
    modifier: Modifier = Modifier,
    group: String,
    groups: List<GroupEntity>,
    onGroupChanged: (String) -> Unit,
) {
    var isVisibleDialog by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier,
        value = group,
        onValueChange = onGroupChanged,
        singleLine = true,
        placeholder = {
            Text(stringResource(R.string.text_field_group))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Groups,
                contentDescription = null
            )
        },
        trailingIcon = {
            Row {
                if (group.isNotEmpty()) {
                    IconButton(onClick = { onGroupChanged("") }) {
                        Icon(
                            imageVector = Icons.Outlined.Cancel,
                            contentDescription = null
                        )
                    }
                }
                IconButton(onClick = { isVisibleDialog = true }) {
                    Icon(
                        imageVector = Icons.Outlined.List,
                        contentDescription = null
                    )
                }
            }
        }
    )
    if (isVisibleDialog) {
        AlertDialog(
            title = { Text(stringResource(R.string.text_field_choose_group)) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Groups,
                    contentDescription = null
                )
            },
            onDismissRequest = { isVisibleDialog = false },
            confirmButton = {
                TextButton(onClick = { isVisibleDialog = false }) {
                    Text(stringResource(R.string.text_button_cancel))
                }
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextBMedium(
                        text = stringResource(R.string.dialog_list_group),
                        textAlign = TextAlign.Center
                    )
                    Divider(modifier = Modifier.padding(vertical = PADDING_MEDIUM))
                    if (groups.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .sizeIn(maxHeight = LIST_GROUPS_MAX_HEIGHT)
                        ) {
                            items(groups) { gp ->
                                DefaultListItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = gp.name,
                                    onClick = {
                                        isVisibleDialog = false
                                        onGroupChanged(gp.name)
                                    }
                                )
                            }
                        }
                    } else EmptyList()
                }
            }
        )
    }
}

@Composable
private fun LanguageTextField(
    modifier: Modifier = Modifier,
    language: Language,
    onLanguageChanged: (Language) -> Unit
) {
    var isExpand by rememberSaveable { mutableStateOf(false) }

    DefaultMenu(
        modifier = modifier,
        expanded = isExpand,
        onExpandedChanged = { isExpand = it},
        text = language.customName,
        placeholder = { Text(stringResource(R.string.text_field_group)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Language,
                contentDescription = null
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Outlined.ExpandMore,
                contentDescription = null
            )
        }
    ) {
        Language.values().forEach { lang ->
            DefaultListItem(modifier = Modifier.fillMaxWidth(), text = lang.customName) {
                onLanguageChanged(lang)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PhoneTextField(
    modifier: Modifier = Modifier,
    phone: String,
    onPhoneChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = phone,
        onValueChange = onPhoneChanged,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        placeholder = { Text(stringResource(R.string.text_field_phone_number)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.PhoneAndroid,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (phone.isNotEmpty()) {
                IconButton(onClick = { onPhoneChanged("") }) {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun RegistrationScreenPreview() {
    TPUMobileTheme {
        RegistrationScreenStateless()
    }
}