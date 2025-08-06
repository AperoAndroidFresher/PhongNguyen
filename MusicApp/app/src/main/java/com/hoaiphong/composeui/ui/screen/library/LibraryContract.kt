package com.hoaiphong.composeui.ui.screen.library

import com.hoaiphong.composeui.comon.SongItemState
import com.hoaiphong.composeui.db.entity.Playlist
import com.hoaiphong.composeui.db.entity.relations.PlaylistWithSongs


sealed interface LibraryIntent {
    data object LoadLocalSongs : LibraryIntent
    data object LoadRemoteSongs : LibraryIntent
    data class ToggleDropdown(val index: Int) : LibraryIntent
    data object DismissDropdown : LibraryIntent

    data class ShowAddToPlaylistDialog(val index: Int) : LibraryIntent
    data object DismissAddToPlaylistDialog : LibraryIntent

    object LoadPlaylists : LibraryIntent
}

data class LibraryState(
    val songs: List<SongItemState> = emptyList(),
    val isLocalSelected: Boolean = true,

    val showAddToPlaylistDialog: Boolean = false,
    val selectedSongIndexForPlaylist: Int? = null,

    val isDialogVisible: Boolean = false,
    val selectedSongIndex: Int? = null,

    val playlists: List<PlaylistWithSongs> = emptyList(),
    val isLoading: Boolean = false,
)

sealed interface LibraryEffect
