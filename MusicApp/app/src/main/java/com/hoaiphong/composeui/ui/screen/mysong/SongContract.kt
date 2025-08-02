package com.hoaiphong.composeui.ui.screen.mysong

import com.hoaiphong.composeui.comon.SongItemState
import com.hoaiphong.composeui.data.model.Song

sealed interface PlaylistIntent {
    data class ToggleView(val isColumn: Boolean) : PlaylistIntent
    data class RemoveSong(val index: Int) : PlaylistIntent
    data class ToggleDropdown(val index: Int) : PlaylistIntent
    data class DismissDropdown(val index: Int) : PlaylistIntent
    object LoadSongs : PlaylistIntent
    data class LoadSpecificSongs(val songs: List<Song>) : PlaylistIntent
}

sealed interface PlaylistEffect {
    data class ShowToast(val message: String) : PlaylistEffect
}

data class PlaylistState(
    val songs: List<SongItemState> = emptyList(),
    val isColumnView: Boolean = true,
    val isLoading: Boolean = false
)