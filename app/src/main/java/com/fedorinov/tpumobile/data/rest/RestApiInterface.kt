package com.fedorinov.tpumobile.data.rest

import com.fedorinov.tpumobile.data.rest.model.request.AuthRequest
import com.fedorinov.tpumobile.data.rest.model.response.AuthResponse
import com.fedorinov.tpumobile.data.rest.model.response.GroupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface RestApiInterface {

    @Headers("Content-Type: application/json")
    @POST(REST_AUTHORIZATION_BY_LOCAL)
    suspend fun authorization(
        @Header("Accept-Language") language: String,
        @Body authRequest: AuthRequest
    ) : Response<AuthResponse>

    @Headers("Content-Type: application/json")
    @GET(REST_GET_GROUPS)
    suspend fun getGroups(
        @Header("Accept-Language") language: String
    ) : Response<List<GroupResponse>>

}