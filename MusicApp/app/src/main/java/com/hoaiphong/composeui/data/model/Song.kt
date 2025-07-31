package com.hoaiphong.composeui.data.model

import android.content.ContentResolver
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.util.Log

data class Song(
    val id: Long,
    val name: String,
    val author: String,
    val duration: String,
    val image: ByteArray?,
    val data: String,
)

fun getAllMp3File(context: Context): List<Song> {
    val songList = mutableListOf<Song>()
    val contentResolver: ContentResolver = context.contentResolver
    val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val selection = "${MediaStore.Audio.Media.DURATION} > 0"
    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.DATA
    )

    val cursor = contentResolver.query(uri, projection, selection, null, null)
    cursor?.use {
        val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
        val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
        val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
        val durationColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
        val dataColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

        while (it.moveToNext()) {
            val id = it.getLong(idColumn)
            val title = it.getString(titleColumn)
            val artist = it.getString(artistColumn)
            val duration = it.getString(durationColumn)
            val data = it.getString(dataColumn)

            // Extract embedded image
            val imageBytes = try {
                val retriever = MediaMetadataRetriever()
                retriever.setDataSource(data)
                val bytes = retriever.embeddedPicture
                retriever.release()
                bytes
            } catch (e: Exception) {
                null
            }
            val song = Song(
                id = id,
                name = title,
                author = artist,
                duration = duration,
                image = imageBytes,
                data = data
            )
            songList.add(song)
        }
    }

    Log.d("SongDebug", "Total songs found: ${songList.size}")
    return songList
}