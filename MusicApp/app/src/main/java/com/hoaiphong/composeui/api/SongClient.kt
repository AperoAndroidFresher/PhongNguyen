package com.hoaiphong.composeui.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SongRetrofitClient {
    private const val BASE_URL = "https://static.apero.vn"

    val imageService: SongApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SongApiService::class.java)
    }


}