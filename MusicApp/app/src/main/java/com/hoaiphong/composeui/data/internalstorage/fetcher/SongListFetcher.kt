package com.hoaiphong.composeui.data.internalstorage.fetcher

import android.content.Context
import com.google.gson.Gson
import com.hoaiphong.composeui.api.song.SongAPIResponse
import com.hoaiphong.composeui.api.song.SongRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class SongListFetcher(private val context: Context) {
    fun fetchAndSaveSongList(
        fileName: String,
        onResult: (List<SongAPIResponse>?) -> Unit,
    ) {
        SongRetrofitClient.build().getSongApiResponse().enqueue(
            object : Callback<List<SongAPIResponse>> {
                override fun onResponse(
                    call: Call<List<SongAPIResponse>>,
                    response: Response<List<SongAPIResponse>>,
                ) {
                    if (response.isSuccessful) {
                        val songList = response.body()
                        if (songList != null) {
                            val file = File(context.filesDir, fileName)
                            file.writeText(Gson().toJson(songList))
                            onResult(songList)
                        } else {
                            onResult(null)
                        }
                    } else {
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<List<SongAPIResponse>>, t: Throwable) {
                    onResult(null)
                }
            },
        )
    }
}
