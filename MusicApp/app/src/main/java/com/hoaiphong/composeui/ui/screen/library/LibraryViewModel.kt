package com.hoaiphong.composeui.ui.screen.library

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.hoaiphong.composeui.comon.SongItemState
import com.hoaiphong.composeui.data.model.PlaylistManager
import com.hoaiphong.composeui.data.model.getAllMp3File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LibraryViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(LibraryState())
    val uiState: StateFlow<LibraryState> = _uiState.asStateFlow()

    fun dispatch(intent: LibraryIntent) {
        when (intent) {
            is LibraryIntent.LoadLocalSongs -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val raw = getAllMp3File(application)
                    val wrapped = raw.map { SongItemState(it) }
                    _uiState.update {
                        it.copy(
                            songs = wrapped,
                            isLocalSelected = true
                        )
                    }
                }
            }

            is LibraryIntent.LoadRemoteSongs -> {
                _uiState.update {
                    it.copy(
                        songs = emptyList(),
                        isLocalSelected = false
                    )
                }
            }

            is LibraryIntent.ToggleDropdown -> {
                _uiState.update {
                    val updated = it.songs.mapIndexed { i, item ->
                        if (i == intent.index) item.copy(isMenuExpanded = !item.isMenuExpanded)
                        else item.copy(isMenuExpanded = false)
                    }
                    it.copy(songs = updated)
                }
            }

            is LibraryIntent.DismissDropdown -> {
                _uiState.update {
                    val updated = it.songs.map { it.copy(isMenuExpanded = false) }
                    it.copy(songs = updated)
                }
            }

            is LibraryIntent.ShowAddToPlaylistDialog -> {
                _uiState.update {
                    it.copy(
                        showAddToPlaylistDialog = true,
                        selectedSongIndexForPlaylist = intent.index,
                        playlists = PlaylistManager.getPlaylists()
                    )
                }
            }

            is LibraryIntent.DismissAddToPlaylistDialog -> {
                _uiState.update {
                    it.copy(
                        showAddToPlaylistDialog = false,
                        selectedSongIndexForPlaylist = null
                    )
                }
            }

            is LibraryIntent.LoadPlaylists -> {
                _uiState.update {
                    it.copy(playlists = PlaylistManager.getPlaylists())
                }
            }
        }
    }

}
