package com.hoaiphong.composeui.ui.screen.myalbum

import com.hoaiphong.composeui.data.model.Playlist


sealed interface PlaylistIntent {
    data object ShowAddPlaylistDialog : PlaylistIntent
    data object DismissAddPlaylistDialog : PlaylistIntent
    data class AddPlaylist(val name: String) : PlaylistIntent
}

data class PlayListState(
    val playlists: List<Playlist> = emptyList(),
    val showAddDialog: Boolean = false
)

sealed interface PlayListEffect
