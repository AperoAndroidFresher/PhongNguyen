package com.hoaiphong.composeui.ui.screen.mysong

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hoaiphong.composeui.data.model.getAllMp3File
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PlaylistViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(PlaylistState())
    val uiState: StateFlow<PlaylistState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<PlaylistEffect>()
    val effect: SharedFlow<PlaylistEffect> = _effect.asSharedFlow()

    init {
        dispatch(PlaylistIntent.LoadSongs)
    }

    fun dispatch(intent: PlaylistIntent) {
        when (intent) {
            is PlaylistIntent.ToggleView -> {
                _uiState.update { it.copy(isColumnView = intent.isColumn) }
            }

            is PlaylistIntent.RemoveSong -> {
                _uiState.update { currentState ->
                    val updatedSongs = currentState.songs.toMutableList()
                    if (intent.index in updatedSongs.indices) {
                        updatedSongs.removeAt(intent.index)
                        viewModelScope.launch {
                            _effect.emit(PlaylistEffect.ShowToast("Đã xoá bài hát"))
                        }
                    }
                    currentState.copy(songs = updatedSongs)
                }
            }

            is PlaylistIntent.LoadSongs -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true) }
                    val songs = getAllMp3File(getApplication())
                    _uiState.update {
                        it.copy(songs = songs, isLoading = false)
                    }
                }
            }
        }
    }
}
