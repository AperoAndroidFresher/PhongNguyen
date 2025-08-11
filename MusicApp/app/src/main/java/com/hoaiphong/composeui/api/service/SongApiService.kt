package com.hoaiphong.composeui.api.service

import com.hoaiphong.composeui.api.response.SongAPIResponse
import retrofit2.Call
import retrofit2.http.GET

interface SongApiService {
    @GET("/techtrek/Remote_audio.json")
    fun getSongApiResponse(): Call<List<SongAPIResponse>>
}
