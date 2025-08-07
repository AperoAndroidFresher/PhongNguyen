package com.hoaiphong.composeui.ui.playlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hoaiphong.composeui.data.repository.impl.PlaylistManager
import com.hoaiphong.composeui.data.local.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayListViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val playlistManager = PlaylistManager(
        playListDAO = db.playListDao(),
        songDAO = db.songDao(),
        crossRefDAO = db.playlistSongCrossRefDAO()
    )

    private val _state = MutableStateFlow(PlayListState())
    val state: StateFlow<PlayListState> = _state.asStateFlow()

    init {
        observePlaylists()
    }

    private fun observePlaylists() {
        viewModelScope.launch(Dispatchers.IO) {
            playlistManager.getPlaylistsWithSongs().collectLatest { playlists ->
                _state.update { it.copy(playlists = playlists) }
            }
        }
    }

    fun dispatch(intent: PlaylistIntent) {
        when (intent) {
            is PlaylistIntent.AddPlaylist -> {
                if (intent.name.isNotBlank()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        playlistManager.addPlaylist(
                            name = intent.name.trim(),
                            ownerUsername = intent.ownerUsername
                        )
                        _state.update { it.copy(showAddDialog = false) }
                    }
                }
            }

            is PlaylistIntent.RemovePlaylist -> {
                viewModelScope.launch(Dispatchers.IO) {
                    playlistManager.removePlaylist(intent.playlistId)
                }
            }

            is PlaylistIntent.RenamePlaylist -> {
                viewModelScope.launch(Dispatchers.IO) {
                    playlistManager.renamePlaylist(
                        playlistId = intent.playlistId,
                        newName = intent.newName
                    )
                    _state.update { it.copy(selectedPlaylistIndexForRename = null) }
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
