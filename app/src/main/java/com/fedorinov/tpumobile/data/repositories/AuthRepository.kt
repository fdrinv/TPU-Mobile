package com.fedorinov.tpumobile.data.repositories

import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.fedorinov.tpumobile.R
import com.fedorinov.tpumobile.data.rest.RestApiTpu
import com.fedorinov.tpumobile.data.rest.model.request.AuthRequest
import com.fedorinov.tpumobile.data.rest.model.response.AuthResponse
import com.fedorinov.tpumobile.logic.utils.DATE_PATTERN
import com.fedorinov.tpumobile.logic.utils.toString
import java.io.InterruptedIOException
import java.util.Date
import java.util.Locale

class AuthRepository(
    private val restApi: RestApiTpu,
    private val connectivityManager: ConnectivityManager
) {

    suspend fun authorization(email: String, password: String, rememberMe: Boolean): AuthResponse? =
        try {
            // 0. Проверит наличие интернет-соединения
            if (checkNetworkStatus()) {
            // 1. Осуществляем запрос к серверу на авторизацию в системе
                val response = restApi.api.authorization(
                    language = Locale.getDefault().toString(),
                    authRequest = AuthRequest(
                        email = email,
                        password = password,
                        rememberMe = rememberMe
                    )
                )
                //  2. Проверяем полученный ответ: так как сервер в данном случае возвращает только код при
                //     авторизации, то достаточно просто дать объект ответа в ViewModel авторизации и
                //     обработать его там
                response.body()
            } else AuthResponse(
                type = "InterruptedIOException",
                date = Date().toString(DATE_PATTERN),
                message = Resources.getSystem().getString(R.string.exception_internet_connection)
            )
        } catch (e: InterruptedIOException) {
            AuthResponse(
                type = "InterruptedIOException: timeout",
                date = Date().toString(DATE_PATTERN),
                message = Resources.getSystem().getString(R.string.exception_timeout)
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

    companion object {
        private val TAG = this::class.simpleName
    }

}