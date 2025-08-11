package com.hoaiphong.composeui.data.internalstorage.Impl

import android.content.Context
import com.hoaiphong.composeui.comon.SongItem
import com.hoaiphong.composeui.data.internalstorage.downloader.SongFileDownloader
import com.hoaiphong.composeui.data.internalstorage.SongJsonStorage
import com.hoaiphong.composeui.data.internalstorage.fetcher.SongListFetcher
import com.hoaiphong.composeui.utils.SongMetadataResolver
import com.hoaiphong.composeui.data.internalstorage.SongStorage
import com.hoaiphong.composeui.data.internalstorage.downloader.INTERNAL_STORAGE_DIR
import com.hoaiphong.composeui.data.local.SongLocal
import java.io.File

class SongStorageImpl(
    private val context: Context,
) : SongStorage {

    private val songListFetcher = SongListFetcher(context)
    private val songFileDownloader = SongFileDownloader(context)
    private val songJsonStorage = SongJsonStorage(context)

    override fun loadRemoteSongsAndDownloadFiles(onDone: (List<SongItem>?) -> Unit) {
        songListFetcher.fetchAndSaveSongList("songs.json") { songList ->
            val finalList = songList ?: songJsonStorage.loadSongListFromFile("songs.json")

            if (finalList != null) {
                songFileDownloader.downloadAllSongs(finalList) {
                    val songStates = finalList.mapIndexed { index, song ->
                        val fileName = "${song.title}.mp3"
                        val localPath = File(context.filesDir, "${INTERNAL_STORAGE_DIR}/$fileName")
                        val finalPath =
                            if (localPath.exists()) {
                                localPath.absolutePath
                            } else song.path

                        val albumArt = SongMetadataResolver.extractAlbumArt(finalPath)

                        SongItem(
                            SongLocal(
                                id = index.toLong(),
                                name = song.title,
                                author = song.artist,
                                duration = song.duration,
                                image = albumArt,
                                data = finalPath,
                            ),
                        )
                    }
                    onDone(songStates)
                }
            } else {
                onDone(null)
            }
        }
    }
}
