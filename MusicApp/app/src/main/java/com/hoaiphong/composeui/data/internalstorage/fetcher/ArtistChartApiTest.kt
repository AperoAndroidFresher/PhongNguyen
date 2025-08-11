package com.hoaiphong.composeui.api.test

import android.util.Log
import com.hoaiphong.composeui.api.client.HomeRetrofitClient
import com.hoaiphong.composeui.api.response.TopArtistsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ArtistApiTest {

    private const val TAG = "ArtistChartApiTest"

    fun logTopArtists() {
        HomeRetrofitClient.build1().getTopArtists()
            .enqueue(object : Callback<TopArtistsResponse> {
                override fun onResponse(
                    call: Call<TopArtistsResponse>,
                    response: Response<TopArtistsResponse>
                ) {
                    if (response.isSuccessful) {
                        val artists = response.body()?.artists?.artist ?: emptyList()
                        artists.forEach { artist ->
                            val lastImageUrl = artist.image.lastOrNull()?.url ?: "No image"
                            Log.v(TAG, "Artist: ${artist.name}, Image: $lastImageUrl")
                        }
                    } else {
                        Log.v(TAG, "Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<TopArtistsResponse>, t: Throwable) {
                    Log.v(TAG, "onFailure: ${t.message}")
                }
            })
    }
}
