package com.fedorinov.tpumobile.data.repositories

import com.fedorinov.tpumobile.App
import com.fedorinov.tpumobile.R
import com.fedorinov.tpumobile.data.common.UserInfo
import com.fedorinov.tpumobile.data.rest.ApiExceptions
import com.fedorinov.tpumobile.data.rest.RestApiTpu
import com.fedorinov.tpumobile.data.rest.model.request.AuthRequest
import com.fedorinov.tpumobile.data.rest.model.request.RegistrationRequest
import com.fedorinov.tpumobile.data.rest.model.response.AuthResponse
import com.fedorinov.tpumobile.data.rest.model.response.RegistrationResponse
import com.fedorinov.tpumobile.logic.utils.DATE_PATTERN
import com.fedorinov.tpumobile.logic.utils.toString
import org.json.JSONObject
import retrofit2.Response
import java.io.InterruptedIOException
import java.net.UnknownHostException
import java.util.Date
import java.util.Locale

class RegistrationRepository(private val restApi: RestApiTpu) {

    suspend fun registration(userInfo: UserInfo): RegistrationResponse  = try {
        // 1. Осуществляем запрос к серверу на регистрацию в системе
        val response = checkAuthResponse(
            restApi.api.registration(
                language = Locale.getDefault().toString(),
                registrationRequest = userInfo.toRegistrationRequest()
            )
        )
        //  2. Проверяем полученный ответ: так как сервер в данном случае возвращает только код при
        //     авторизации, то достаточно просто дать объект ответа в ViewModel авторизации и
        //     обработать его там
        response
    } catch (unknownHostException: UnknownHostException) {
        RegistrationResponse(
            type = UnknownHostException::class.simpleName!!,
            date = Date().toString(DATE_PATTERN),
            message = App.getAppResources()?.getString(R.string.exception_no_internet_connection) ?: "No internet connection"
        )
    } catch (interruptedIOException: InterruptedIOException) {
        RegistrationResponse(
            type = InterruptedIOException::class.simpleName!!,
            date = Date().toString(DATE_PATTERN),
            message = App.getAppResources()?.getString(R.string.exception_timeout) ?: "Timeout exceeded"
        )
    }

    /**
     * Первичная проверка ответа от сервера.
     */
    private fun checkAuthResponse(response: Response<RegistrationResponse>): RegistrationResponse {
        if (response.code() == 400) {
            val jObjError = JSONObject(response.errorBody()!!.string())
            return RegistrationResponse(
                type = jObjError.getString("type"),
                date = Date().toString(DATE_PATTERN),
                message = jObjError.getString("message")
            )
        }

        if (response.code() != 200 && response.code() != 201) {
            return RegistrationResponse(
                type = ApiExceptions.OtherException::class.simpleName!!,
                date = Date().toString(DATE_PATTERN),
                message = ApiExceptions.OtherException(response.code()).message!!
            )
        }

        if (response.body() == null) {
            return RegistrationResponse(
                type = ApiExceptions.EmptyResponseException::class.simpleName!!,
                date = Date().toString(DATE_PATTERN),
                message = ApiExceptions.EmptyResponseException.message!!
            )
        }

        return response.body()!!
    }

}