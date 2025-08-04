package com.hoaiphong.composeui.ui.screen.myalbum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoaiphong.composeui.data.model.PlaylistManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayListViewModel : ViewModel() {
    private val _state = MutableStateFlow(PlayListState())
    val state: StateFlow<PlayListState> = _state.asStateFlow()

    fun dispatch(intent: PlaylistIntent) {
        when (intent) {
            is PlaylistIntent.AddPlaylist -> {
                if (intent.name.isNotBlank()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        PlaylistManager.addPlaylist(intent.name.trim())
                        val updated = PlaylistManager.getPlaylists().toList()
                        _state.update {
                            it.copy(
                                playlists = updated,
                                showAddDialog = false
                            )
                        }
                    }
                }
            }

            is PlaylistIntent.RemovePlaylist -> {
                viewModelScope.launch(Dispatchers.IO) {
                    PlaylistManager.removePlaylist(intent.name)
                    val updated = PlaylistManager.getPlaylists().toList()
                    _state.update { it.copy(playlists = updated) }
                }
            }

            is PlaylistIntent.RenamePlaylist -> {
                viewModelScope.launch(Dispatchers.IO) {
                    PlaylistManager.renamePlaylist(intent.oldName, intent.newName)
                    val updated = PlaylistManager.getPlaylists().toList()
                    _state.update {
                        it.copy(
                            playlists = updated,
                            selectedPlaylistIndexForRename = null
                        )
                    }
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
