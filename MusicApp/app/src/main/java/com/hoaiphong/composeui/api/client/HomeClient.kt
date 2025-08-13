package com.hoaiphong.composeui.api.client


import com.hoaiphong.composeui.api.service.AlbumApiService
import com.hoaiphong.composeui.api.service.ArtistApiService
import com.hoaiphong.composeui.api.service.TrackApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object HomeRetrofitClient {
    private const val BASE_URL = "https://ws.audioscrobbler.com/2.0/"
    private const val REQUEST_TIMEOUT = 5L

    private val retrofit by lazy { buildRetrofit() }

    fun buildAlbum(): AlbumApiService {
        return HomeRetrofitClient.retrofit.create(AlbumApiService::class.java)
    }
    fun buildArtist(): ArtistApiService {
        return HomeRetrofitClient.retrofit.create(ArtistApiService::class.java)
    }
    fun buildTrack(): TrackApiService {
        return HomeRetrofitClient.retrofit.create(TrackApiService::class.java)
    }

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(buildClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun buildClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

}
