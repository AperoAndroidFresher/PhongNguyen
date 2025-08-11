package com.hoaiphong.composeui.api.test

import android.util.Log
import com.hoaiphong.composeui.api.client.HomeRetrofitClient
import com.hoaiphong.composeui.api.response.TopAlbumsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AlbumApiTest {

    private const val TAG = "AlbumApiTest"

    fun logTopAlbums() {
        HomeRetrofitClient.build().getTopAlbums()
            .enqueue(object : Callback<TopAlbumsResponse> {
                override fun onResponse(
                    call: Call<TopAlbumsResponse>,
                    response: Response<TopAlbumsResponse>
                ) {
                    if (response.isSuccessful) {
                        val albums = response.body()?.topalbums?.album ?: emptyList()
                        albums.forEach { album ->
                            val lastImageUrl = album.image.lastOrNull()?.url ?: "No image"
                            Log.v(
                                TAG,
                                "Album: ${album.name}, Artist: ${album.artist.name}, Image: $lastImageUrl"
                            )
                        }
                    } else {
                        Log.v(TAG, "Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<TopAlbumsResponse>, t: Throwable) {
                    Log.v(TAG, "onFailure: ${t.message}")
                }
            })
    }
}
