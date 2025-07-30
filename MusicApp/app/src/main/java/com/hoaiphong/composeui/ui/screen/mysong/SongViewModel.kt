package com.hoaiphong.composeui.ui.screen.mysong

import androidx.lifecycle.ViewModel
import com.hoaiphong.composeui.data.model.songList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PlaylistViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        PlaylistState(
            songs = songList,
            isColumnView = true
        )
    )
    val uiState: StateFlow<PlaylistState> = _uiState

    fun onEvent(intent: PlaylistIntent) {
        when (intent) {
            is PlaylistIntent.ToggleView -> {
                _uiState.update { it.copy(isColumnView = intent.isColumn) }
            }

            is PlaylistIntent.RemoveSong -> {
                _uiState.update {
                    val updatedSongs = it.songs.toMutableList().apply {
                        removeAt(intent.index)
                    }
                    it.copy(songs = updatedSongs)
                }
            }
        }
    }
}