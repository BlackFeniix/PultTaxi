package com.hito.nikolay.pulttaxi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val URL_API = "https://dev.pulttaxi.ru/api/"

    private fun retrofitService(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URL_API)
            .build()
    }

    val pultTaxiServiceApi: PultTaxiService by lazy {
        retrofitService().create(PultTaxiService::class.java)
    }
}