package com.hoaiphong.composeui.ui.screen.library

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.hoaiphong.composeui.comon.SongItemState
import com.hoaiphong.composeui.data.model.getAllMp3File
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class LibraryViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    private val _uiState = MutableStateFlow(LibraryState())
    val uiState: StateFlow<LibraryState> = _uiState.asStateFlow()

    fun dispatch(intent: LibraryIntent) {
        when (intent) {
            is LibraryIntent.LoadLocalSongs -> {
                val raw = getAllMp3File(context)
                val wrapped = raw.map { SongItemState(it) }
                _uiState.value = _uiState.value.copy(
                    songs = wrapped,
                    isLocalSelected = true
                )
            }

            is LibraryIntent.LoadRemoteSongs -> {
                _uiState.value = _uiState.value.copy(
                    songs = emptyList(),
                    isLocalSelected = false
                )
            }

            is LibraryIntent.ToggleDropdown -> {
                val updated = _uiState.value.songs.mapIndexed { i, item ->
                    if (i == intent.index) item.copy(isMenuExpanded = !item.isMenuExpanded)
                    else item.copy(isMenuExpanded = false)
                }
                _uiState.value = _uiState.value.copy(songs = updated)
            }

            is LibraryIntent.DismissDropdown -> {
                val updated = _uiState.value.songs.map { it.copy(isMenuExpanded = false) }
                _uiState.value = _uiState.value.copy(songs = updated)
            }

            is LibraryIntent.ShowAddToPlaylistDialog -> {
                _uiState.update {
                    it.copy(
                        showAddToPlaylistDialog = true,
                        selectedSongIndexForPlaylist = intent.index
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

        }
    }
}
