package com.hoaiphong.composeui.comon

import com.hoaiphong.composeui.data.local.Song

data class SongItemState(
    val song: Song,
    val isMenuExpanded: Boolean = false
)
