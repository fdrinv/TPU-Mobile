package com.fedorinov.tpumobile.data.repositories

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.fedorinov.tpumobile.App
import com.fedorinov.tpumobile.R
import com.fedorinov.tpumobile.data.rest.ApiExceptions.*
import com.fedorinov.tpumobile.data.rest.RestApiTpu
import com.fedorinov.tpumobile.data.rest.model.request.AuthRequest
import com.fedorinov.tpumobile.data.rest.model.response.AuthResponse
import com.fedorinov.tpumobile.logic.utils.DATE_PATTERN
import com.fedorinov.tpumobile.logic.utils.toString
import org.json.JSONObject
import retrofit2.Response
import java.io.InterruptedIOException
import java.net.UnknownHostException
import java.util.Date
import java.util.Locale

class AuthRepository(
    private val restApi: RestApiTpu,
    private val connectivityManager: ConnectivityManager
) {

    suspend fun authorization(email: String, password: String, rememberMe: Boolean): AuthResponse  = try {
        // 1. Осуществляем запрос к серверу на авторизацию в системе
        val response = checkAuthResponse(
            restApi.api.authorization(
                language = Locale.getDefault().toString(),
                authRequest = AuthRequest(
                    email = email,
                    password = password,
                    rememberMe = rememberMe
                )
            )
        )
        //  2. Проверяем полученный ответ: так как сервер в данном случае возвращает только код при
        //     авторизации, то достаточно просто дать объект ответа в ViewModel авторизации и
        //     обработать его там
        response
    } catch (unknownHostException: UnknownHostException) {
        AuthResponse(
            type = UnknownHostException::class.simpleName!!,
            date = Date().toString(DATE_PATTERN),
            message = App.getAppResources()?.getString(R.string.exception_no_internet_connection) ?: "No internet connection"
        )
    } catch (interruptedIOException: InterruptedIOException) {
        AuthResponse(
            type = InterruptedIOException::class.simpleName!!,
            date = Date().toString(DATE_PATTERN),
            message = App.getAppResources()?.getString(R.string.exception_timeout) ?: "Timeout exceeded"
        )
    }

    /**
     * Проверяет наличие интернет-соединения
     */
    private fun checkNetworkStatus(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    /**
     * Первичная проверка ответа от сервера.
     */
    private fun checkAuthResponse(response: Response<AuthResponse>): AuthResponse {
        if (response.code() == 400) {
            val jObjError = JSONObject(response.errorBody()!!.string())
            return AuthResponse(
                type = jObjError.getString("type"),
                date = Date().toString(DATE_PATTERN),
                message = jObjError.getString("message")
            )
        }

        if (response.code() != 200 && response.code() != 201) {
            return AuthResponse(
                type = OtherException::class.simpleName!!,
                date = Date().toString(DATE_PATTERN),
                message = OtherException(response.code()).message!!
            )
        }

        if (response.body() == null) {
            return AuthResponse(
                type = EmptyResponseException::class.simpleName!!,
                date = Date().toString(DATE_PATTERN),
                message = EmptyResponseException.message!!
            )
        }

        return response.body()!!
    }

    companion object {
        private val TAG = AuthRepository::class.simpleName
    }

}