package com.hoaiphong.composeui.ui.library

import com.hoaiphong.composeui.comon.SongItemState
import com.hoaiphong.composeui.data.local.model.entity.Song
import com.hoaiphong.composeui.data.local.model.entity.relations.PlaylistWithSongs


sealed interface LibraryIntent {
    data object LoadLocalSongs : LibraryIntent
    data object LoadRemoteSongs : LibraryIntent
    data class ToggleDropdown(val index: Int) : LibraryIntent
    data object DismissDropdown : LibraryIntent

    data class ShowAddToPlaylistDialog(val index: Int) : LibraryIntent
    data object DismissAddToPlaylistDialog : LibraryIntent

    object LoadPlaylists : LibraryIntent
    data class PlaySong(val index: Int) : LibraryIntent
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
    val hasNetworkError: Boolean = false
)

sealed interface LibraryEffect{
    data class StartMusicService(
        val playlist: List<Song>,
        val startIndex: Int
    ) : LibraryEffect
}
