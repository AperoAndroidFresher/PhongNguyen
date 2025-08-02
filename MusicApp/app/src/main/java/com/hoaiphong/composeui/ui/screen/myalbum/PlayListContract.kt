package com.hoaiphong.composeui.ui.screen.myalbum

import com.hoaiphong.composeui.data.model.Playlist


sealed interface PlaylistIntent {
    data object ShowAddPlaylistDialog : PlaylistIntent
    data object DismissAddPlaylistDialog : PlaylistIntent
    data class AddPlaylist(val name: String) : PlaylistIntent

    data class RenamePlaylist(val oldName: String, val newName: String) : PlaylistIntent
    data class RemovePlaylist(val name: String) : PlaylistIntent

    data class ShowDropdown(val index: Int) : PlaylistIntent

    object DismissDropdown : PlaylistIntent

    data class ShowRenameDialog(val index: Int) : PlaylistIntent

    object DismissRenameDialog : PlaylistIntent
}

data class PlayListState(
    val playlists: List<Playlist> = emptyList(),
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
