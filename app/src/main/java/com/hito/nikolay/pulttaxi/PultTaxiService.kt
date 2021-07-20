package com.hito.nikolay.pulttaxi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PultTaxiService {

    @GET("requestSMSCodeClient")
    fun requestSMSCode(@Query("phone_number") phoneNumber: String): Call<SmsCodeClientResponse>

    @POST("authenticateClients")
    fun getJSONFromSMS(
        @Query("phone_number") phoneNumber: String,
        @Query("password") codeSMS: String
    ): Call<AuthenticateClientsToken>

    @GET("client/getInfo")
    fun getUserInformation(@Query("token") token: String): Call<GetInfoClient>
}