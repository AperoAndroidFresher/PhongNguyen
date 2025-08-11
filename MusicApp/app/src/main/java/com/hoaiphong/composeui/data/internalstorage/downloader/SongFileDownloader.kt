package com.hoaiphong.composeui.data.internalstorage.downloader

import android.content.Context
import com.hoaiphong.composeui.api.response.SongAPIResponse
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import java.io.File
import java.io.FileOutputStream

const val INTERNAL_STORAGE_DIR = "phongnh/song"

class SongFileDownloader(private val context: Context) {

    private val client = OkHttpClient()

    fun downloadAllSongs(
        songs: List<SongAPIResponse>,
        onComplete: () -> Unit,
    ) {
        if (songs.isEmpty()) {
            onComplete()
            return
        }

        var completed = 0
        val total = songs.size

        songs.forEach { song ->
            val fileName = "${song.title}.mp3"
            downloadMp3(fileName, song.path) {
                completed++
                if (completed == total) {
                    onComplete()
                }
            }
        }
    }

    private fun downloadMp3(
        fileName: String,
        url: String,
        onDone: (Boolean) -> Unit,
    ) {
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onDone(false)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        onDone(false)
                        return
                    }

                    val bytes = response.body?.bytes()
                    if (bytes != null) {
                        val directory = File(context.filesDir, INTERNAL_STORAGE_DIR)
                        if (!directory.exists()) {
                            directory.mkdirs()
                        }
                        val file = File(directory, fileName)
                        FileOutputStream(file).use {
                            it.write(bytes)
                        }

                        onDone(true)
                    } else {
                        onDone(false)
                    }
                }
            },
        )
    }
}
