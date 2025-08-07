package com.hoaiphong.composeui.ui.playlist

import com.hoaiphong.composeui.data.local.model.entity.relations.PlaylistWithSongs


sealed interface PlaylistIntent {
    data object ShowAddPlaylistDialog : PlaylistIntent
    data object DismissAddPlaylistDialog : PlaylistIntent
    data class AddPlaylist(val name: String, val ownerUsername: String) : PlaylistIntent

    data class RenamePlaylist(val playlistId: Long, val newName: String) : PlaylistIntent
    data class RemovePlaylist(val playlistId: Long) : PlaylistIntent

    data class ShowDropdown(val index: Int) : PlaylistIntent
    object DismissDropdown : PlaylistIntent

    data class ShowRenameDialog(val index: Int) : PlaylistIntent
    object DismissRenameDialog : PlaylistIntent
}

data class PlayListState(
    val playlists: List<PlaylistWithSongs> = emptyList(),
    val showAddDialog: Boolean = false,
    val selectedPlaylistIndexForDropdown: Int? = null,
    val selectedPlaylistIndexForRename: Int? = null
)

sealed class MenuState {
    object None : MenuState()
    object Expanded : MenuState()
    object ShowRenameDialog : MenuState()
}

sealed interface PlayListEffect
