package com.hoaiphong.composeui.comon

import com.hoaiphong.composeui.data.model.Song

data class SongItemState(
    val song: Song,
    val isMenuExpanded: Boolean = false
)