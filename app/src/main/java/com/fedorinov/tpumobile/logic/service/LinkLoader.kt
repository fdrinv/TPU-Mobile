package com.fedorinov.tpumobile.logic.service

import com.fedorinov.tpumobile.data.rest.RestApiTpu

/**
 * Сервис для работы со ссылками, на данном сервис лежит ответственность с:
 * - хранением в оперативной памяти ссылок.
 * - кеширование ссылок.
 * - запрос ссылок с сервера.
 */
class LinkLoader(
    private val restApi: RestApiTpu
) {

}