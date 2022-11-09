package com.example.spacexapitest.model.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object SpaceXNetwork {
    val spaceXApi: SpaceXApi by lazy {
        initRetrofit()
    }

    private fun initRetrofit(): SpaceXApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SpaceXApi::class.java)
    }

    private fun createClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val oKHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return oKHttpClient
    }

}