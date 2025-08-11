package com.hoaiphong.composeui.api.service

import com.hoaiphong.composeui.api.response.TopArtistsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistApiService {
    @GET("?format=json&method=chart.gettopartists")
    fun getTopArtists(
        @Query("api_key") apiKey: String = API_KEY,
    ): Call<TopArtistsResponse>
    @GET("?format=json&method=chart.gettopartists")
    fun getTop5Artists(
        @Query("api_key") apiKey: String = API_KEY,        
        @Query("limit") limit: Int = 5,
    ): Call<TopArtistsResponse>
    companion object {
        const val API_KEY = "e65449d181214f936368984d4f4d4ae8"
    }
}
