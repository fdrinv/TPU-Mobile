package com.fedorinov.tpumobile.data.rest

import com.fedorinov.tpumobile.App
import com.fedorinov.tpumobile.R

/**
 * Ошибки API.
 */
sealed class ApiExceptions(msg: String): Exception(msg) {

    object BadCredentials : ApiExceptions("Wrong login or password")
    object EmptyResponseException : ApiExceptions(App.getAppResources()?.getString(R.string.exception_empty_response) ?: "Server returned an empty response")
    data class OtherException(val code: Int) : ApiExceptions(App.getAppResources()?.getString(R.string.exception_other_response, code) ?: "Server returned a response with the code $code")
}