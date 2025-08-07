package com.hoaiphong.composeui.comon

import com.hoaiphong.composeui.data.local.SongLocal

data class SongItemState(
    val song: SongLocal,
    val isMenuExpanded: Boolean = false
)
