package com.hoaiphong.composeui.api.service

import com.hoaiphong.composeui.api.response.TopTracksResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackApiService {

    @GET("?format=json&method=artist.getTopTracks")
    fun getAllTopTracks(
        @Query("mbid") mbid: String = MBID,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<TopTracksResponse>

    @GET("?format=json&method=artist.getTopTracks")
    fun getTop5Tracks(
        @Query("mbid") mbid: String = MBID,
        @Query("limit") limit: Int = 5,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<TopTracksResponse>

    companion object {
        const val API_KEY = "e65449d181214f936368984d4f4d4ae8"
        const val MBID = "f9b593e6-4503-414c-99a0-46595ecd2e23"
    }
}
