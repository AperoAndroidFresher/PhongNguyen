package com.hoaiphong.composeui.data.internalstorage

import com.hoaiphong.composeui.comon.SongItemState

interface SongStorage {
    fun loadRemoteSongsAndDownloadFiles(onDone: (List<SongItemState>?) -> Unit)
}
