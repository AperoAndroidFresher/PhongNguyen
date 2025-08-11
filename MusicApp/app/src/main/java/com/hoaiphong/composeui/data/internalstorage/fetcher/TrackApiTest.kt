package com.hoaiphong.composeui.data.internalstorage.fetcher

import android.util.Log
import com.hoaiphong.composeui.api.client.HomeRetrofitClient
import com.hoaiphong.composeui.api.response.TopTracksResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object TrackApiTest {

    private const val TAG = "TrackApiTest"

    fun logTopTracks() {
        val call = HomeRetrofitClient.build2().getTop5Tracks()

        call.enqueue(object : Callback<TopTracksResponse> {
            override fun onResponse(
                call: Call<TopTracksResponse>,
                response: Response<TopTracksResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        val body = response.body()
                        body?.toptracks?.track?.forEach {track ->
                            val firstImageUrl = track.image.lastOrNull()?.url ?: "No image"
                            Log.v(
                                TAG,
                                "Track: ${track.name}, Artist: ${track.artist.name}, " +
                                "Listeners: ${track.listeners}, Image: $firstImageUrl"
                            )
                        }
                    }
                    response.code() == 400 -> Log.v(TAG, "Bad Request")
                    response.code() == 401 -> Log.v(TAG, "Unauthorized")
                    response.code() == 403 -> Log.v(TAG, "Forbidden")
                    response.code() == 404 -> Log.v(TAG, "Not Found")
                    response.code() == 500 -> Log.v(TAG, "Internal Server Error")
                    else -> Log.v(TAG, "Unknown Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopTracksResponse>, t: Throwable) {
                Log.v(TAG, "onFailure: ${t.message}")
            }
        })
    }
}
