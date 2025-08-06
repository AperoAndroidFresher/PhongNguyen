package com.hoaiphong.composeui.ui.mysong

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hoaiphong.composeui.comon.SongItemState
import com.hoaiphong.composeui.data.model.PlaylistManager
import com.hoaiphong.composeui.data.model.getAllMp3File
import com.hoaiphong.composeui.data.model.toModel
import com.hoaiphong.composeui.data.local.room.AppDatabase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PlaylistViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val playlistManager = PlaylistManager(
        db.playListDao(),
        db.songDao(),
        db.playlistSongCrossRefDAO()
    )

    private val _uiState = MutableStateFlow(PlaylistState())
    val uiState: StateFlow<PlaylistState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<PlaylistEffect>()
    val effect: SharedFlow<PlaylistEffect> = _effect.asSharedFlow()

    init {
        dispatch(PlaylistIntent.LoadSongs)
    }

    fun loadPlaylistById(playlistId: Long) {
        viewModelScope.launch {
            val playlistWithSongs = playlistManager.getPlaylistWithSongsById(playlistId)
            playlistWithSongs?.let {
                val modelSongs = playlistWithSongs.songs.map { it.toModel() }
                _uiState.update { oldState ->
                    oldState.copy(
                        songs = modelSongs.map { SongItemState(it) },
                        playlistWithSongs = playlistWithSongs
                    )
                }
            }
        }
    }

    fun dispatch(intent: PlaylistIntent) {
        when (intent) {
            is PlaylistIntent.ToggleView -> {
                _uiState.update { it.copy(isColumnView = intent.isColumn) }
            }

            is PlaylistIntent.RemoveSong -> {
                viewModelScope.launch {
                    val currentState = _uiState.value
                    val playlistWithSongs = currentState.playlistWithSongs
                    if (playlistWithSongs == null) return@launch
                    if (intent.index !in currentState.songs.indices) return@launch

                    val songItemState = currentState.songs[intent.index]
                    val songId = songItemState.song.id
                    val playlistId = playlistWithSongs.playlist.playlistId

                    playlistManager.removeSongFromPlaylist(playlistId, songId)
                    val updatedPlaylist = playlistManager.getPlaylistWithSongsById(playlistId)
                    if (updatedPlaylist != null) {
                        val modelSongs = updatedPlaylist.songs.map { it.toModel() }
                        _uiState.update { old ->
                            old.copy(
                                songs = modelSongs.map { SongItemState(it) },
                                playlistWithSongs = updatedPlaylist
                            )
                        }
                    } else {
                        _uiState.update { old ->
                            val updated = old.songs.toMutableList().apply { removeAt(intent.index) }
                            old.copy(songs = updated)
                        }
                    }
                }
            }

            is PlaylistIntent.LoadSongs -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true) }
                    val songs = getAllMp3File(getApplication()).map { SongItemState(song = it) }
                    _uiState.update {
                        it.copy(songs = songs, isLoading = false)
                    }
                }
            }

            is PlaylistIntent.ToggleDropdown -> {
                _uiState.update { state ->
                    val updated = state.songs.mapIndexed { index, item ->
                        if (index == intent.index) {
                            item.copy(isMenuExpanded = !item.isMenuExpanded)
                        } else {
                            item.copy(isMenuExpanded = false)
                        }
                    }
                    state.copy(songs = updated)
                }
            }

            is PlaylistIntent.DismissDropdown -> {
                _uiState.update { state ->
                    val updated = state.songs.map {
                        it.copy(isMenuExpanded = false)
                    }
                    state.copy(songs = updated)
                }
            }

            is PlaylistIntent.LoadSpecificSongs -> {
                _uiState.update {
                    it.copy(songs = intent.songs.map { song ->
                        SongItemState(song = song)
                    })
                }
            }
        }
    }
}
