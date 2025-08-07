package com.hoaiphong.composeui.utils

import android.media.MediaMetadataRetriever

object SongMetadataResolver {
    fun extractAlbumArt(filePath: String): ByteArray? {
        return try {
            MediaMetadataRetriever().run {
                setDataSource(filePath)
                val art = embeddedPicture
                release()
                art
            }
        } catch (e: Exception) {
            null
        }
    }
}
