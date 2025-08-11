package com.hoaiphong.composeui.comon

import com.hoaiphong.composeui.data.local.SongLocal

data class SongItem(
    val song: SongLocal,
    val isMenuExpanded: Boolean = false
)
