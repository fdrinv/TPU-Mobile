package com.fedorinov.tpumobile.data.rest

// - Основная ссылка для REST-запросов
const val REST_INTERNATIONALS_TPU_API_URL = "https://internationals.tpu.ru:8080/api/"

// - Авторизация в системе через почту и пароль
const val REST_AUTHORIZATION_BY_LOCAL = "auth/local/login"
// - Регистрация в системе
const val REST_REGISTRATION_BY_LOCAL = "auth/local/registration"

// - Получение учебных групп
const val REST_GET_GROUPS = "studyGroup"
// - Получение кнопок меню
const val REST_GET_LINKS = "menu"
// - Получение статьи по ID
const val REST_GET_ARTICLE = "article"