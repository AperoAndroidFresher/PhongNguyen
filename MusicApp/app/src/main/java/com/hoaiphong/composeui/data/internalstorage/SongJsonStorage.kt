package com.hoaiphong.composeui.data.internalstorage

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hoaiphong.composeui.api.song.SongAPIResponse
import java.io.File

class SongJsonStorage(private val context: Context) {

    fun loadSongListFromFile(fileName: String): List<SongAPIResponse>? {
        val file = File(context.filesDir, fileName)
        if (!file.exists()) return null

        return try {
            val json = file.readText()
            val type = object : TypeToken<List<SongAPIResponse>>() {}.type
            Gson().fromJson(json, type)
        } catch (e: Exception) {
            null
        }
    }
}
