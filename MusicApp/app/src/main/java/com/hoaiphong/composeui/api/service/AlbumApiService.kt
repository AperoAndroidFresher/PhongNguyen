package com.hoaiphong.composeui.api.service

import com.hoaiphong.composeui.api.response.TopAlbumsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumApiService {
    @GET("?format=json&method=artist.getTopAlbums")
    fun getTopAlbums(
        @Query("mbid") mbid: String = MBID,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<TopAlbumsResponse>

    @GET("?format=json&method=artist.getTopAlbums")
    fun getTop6Albums(
        @Query("mbid") mbid: String = MBID,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("limit") limit: Int = 6,
    ): Call<TopAlbumsResponse>

    companion object {
        const val API_KEY = "e65449d181214f936368984d4f4d4ae8"
        const val MBID = "f9b593e6-4503-414c-99a0-46595ecd2e23"
    }
}
