package com.hoaiphong.composeui.ui.screen.mysong

import com.hoaiphong.composeui.data.model.Song

sealed interface PlaylistIntent {
    data class ToggleView(val isColumn: Boolean) : PlaylistIntent
    data class RemoveSong(val index: Int) : PlaylistIntent
}

data class PlaylistState(
    val songs: List<Song> = emptyList(),
    val isColumnView: Boolean = true,
)