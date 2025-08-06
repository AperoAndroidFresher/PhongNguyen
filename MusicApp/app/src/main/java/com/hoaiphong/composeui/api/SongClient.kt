package com.hoaiphong.composeui.api


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object SongRetrofitClient {
    private const val BASE_URL = "https://static.apero.vn"
    private const val REQUEST_TIMEOUT = 30L

    private val retrofit by lazy { buildRetrofit() }

    fun build(): SongApiService{return retrofit.create(SongApiService::class.java)}

    private fun buildRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(buildClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun buildClient(): OkHttpClient{
        return OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

}