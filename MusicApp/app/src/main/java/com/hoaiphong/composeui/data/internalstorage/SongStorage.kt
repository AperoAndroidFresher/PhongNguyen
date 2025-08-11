package com.hoaiphong.composeui.data.internalstorage

import com.hoaiphong.composeui.comon.SongItem

interface SongStorage {
    fun loadRemoteSongsAndDownloadFiles(onDone: (List<SongItem>?) -> Unit)
}
