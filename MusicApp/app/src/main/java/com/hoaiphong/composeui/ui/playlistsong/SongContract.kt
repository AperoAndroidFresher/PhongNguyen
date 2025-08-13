package com.hoaiphong.composeui.ui.playlistsong

import com.hoaiphong.composeui.comon.SongItem
import com.hoaiphong.composeui.data.local.SongLocal
import com.hoaiphong.composeui.data.local.model.entity.relations.PlaylistWithSongs

sealed interface PlaylistIntent {
    data class ToggleView(val isColumn: Boolean) : PlaylistIntent
    data class RemoveSong(val index: Int) : PlaylistIntent
    data class ToggleDropdown(val index: Int) : PlaylistIntent
    data class DismissDropdown(val index: Int) : PlaylistIntent
    object LoadSongs : PlaylistIntent
    data class LoadSpecificSongs(val songs: List<SongLocal>, val playlistName: String) : PlaylistIntent
    data class PlaySong(val index: Int) : PlaylistIntent
    data class MoveSong(val fromIndex: Int, val toIndex: Int) : PlaylistIntent
    data class ConfirmSort(val playlistId: Long) : PlaylistIntent
}

sealed interface PlaylistEffect {
    data class ShowToast(val message: String) : PlaylistEffect
    data class StartMusicService(
        val playlistIds: List<Long>,
        val startIndex: Int,
        val artist: String? = null,
        val image: ByteArray? = null
    ) : PlaylistEffect
}

data class PlaylistState(
    val songs: List<SongItem> = emptyList(),
    val isColumnView: Boolean = true,
    val isLoading: Boolean = false,
    val playlistWithSongs: PlaylistWithSongs? = null,
    val playlists: List<PlaylistWithSongs> = emptyList(),
    val selectedSongIndex: Int? = null,
)

