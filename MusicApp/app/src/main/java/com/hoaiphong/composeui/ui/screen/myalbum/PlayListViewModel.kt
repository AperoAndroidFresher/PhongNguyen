package com.hoaiphong.composeui.ui.screen.myalbum

import androidx.lifecycle.ViewModel
import com.hoaiphong.composeui.data.model.PlaylistManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
class PlayListViewModel : ViewModel() {
    private val _state = MutableStateFlow(PlayListState())
    val state: StateFlow<PlayListState> = _state.asStateFlow()

    fun dispatch(intent: PlaylistIntent) {
        when (intent) {
            is PlaylistIntent.AddPlaylist -> {
                if (intent.name.isNotBlank()) {
                    PlaylistManager.addPlaylist(intent.name.trim())
                    _state.update {
                        it.copy(
                            playlists = PlaylistManager.getPlaylists().toList(),
                            showAddDialog = false
                        )
                    }
                }
            }

            is PlaylistIntent.RemovePlaylist -> {
                PlaylistManager.removePlaylist(intent.name)
                _state.update {
                    it.copy(
                        playlists = PlaylistManager.getPlaylists().toList()
                    )
                }
            }

            is PlaylistIntent.RenamePlaylist -> {
                PlaylistManager.renamePlaylist(intent.oldName, intent.newName)
                _state.update {
                    it.copy(
                        playlists = PlaylistManager.getPlaylists().toList(),
                        selectedPlaylistIndexForRename = null
                    )
                }
            }

            PlaylistIntent.ShowAddPlaylistDialog -> {
                _state.update { it.copy(showAddDialog = true) }
            }

            PlaylistIntent.DismissAddPlaylistDialog -> {
                _state.update { it.copy(showAddDialog = false) }
            }

            is PlaylistIntent.ShowDropdown -> {
                _state.update {
                    it.copy(
                        selectedPlaylistIndexForDropdown = intent.index,
                        selectedPlaylistIndexForRename = null
                    )
                }
            }

            PlaylistIntent.DismissDropdown -> {
                _state.update { it.copy(selectedPlaylistIndexForDropdown = null) }
            }

            is PlaylistIntent.ShowRenameDialog -> {
                _state.update {
                    it.copy(
                        selectedPlaylistIndexForRename = intent.index,
                        selectedPlaylistIndexForDropdown = null
                    )
                }
            }

            PlaylistIntent.DismissRenameDialog -> {
                _state.update { it.copy(selectedPlaylistIndexForRename = null) }
            }
        }
    }
}
