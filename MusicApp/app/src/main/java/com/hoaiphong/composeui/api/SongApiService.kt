package com.hoaiphong.composeui.api

import retrofit2.Call
import retrofit2.http.GET


interface SongApiService {
    @GET("Remote_audio.json")
    fun getPhotos(): Call<List<SongAPIResponse>>
}