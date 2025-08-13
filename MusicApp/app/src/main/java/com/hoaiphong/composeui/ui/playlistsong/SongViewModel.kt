package com.hoaiphong.composeui.ui.playlistsong

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hoaiphong.composeui.comon.SongItem
import com.hoaiphong.composeui.data.local.getAllMp3File
import com.hoaiphong.composeui.data.local.toModel
import com.hoaiphong.composeui.data.local.room.AppDatabase
import com.hoaiphong.composeui.data.local.toEntity
import com.hoaiphong.composeui.data.repository.impl.PlaylistRepositoryImpl
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PlaylistViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val playlistManager = PlaylistRepositoryImpl(
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
                        songs = modelSongs.map { SongItem(it) },
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
                                songs = modelSongs.map { SongItem(it) },
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
                    val songs = getAllMp3File(getApplication()).map { SongItem(song = it) }
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
                        SongItem(song = song)
                    })
                }
            }

            is PlaylistIntent.PlaySong -> {
                val songs = _uiState.value.songs
                val index = intent.index
                if (index !in songs.indices) return

                val songItem = songs[index]
                val song = songItem.song
                val artist = song.author.ifEmpty { "Unknown Artist" }
                val image = song.image

                val playlistEntities = songs.map { it.song.toEntity() }
                val playlistIds = playlistEntities.map { it.songId }
                viewModelScope.launch {
                    _effect.emit(
                        PlaylistEffect.StartMusicService(
                            playlistIds = playlistIds,
                            startIndex = index,
                            artist = artist,
                            image = image
                        )
                    )
                }
            }
            is PlaylistIntent.MoveSong -> {
                _uiState.update { state ->
                    val updatedSongs = state.songs.toMutableList()
                    val song = updatedSongs.removeAt(intent.fromIndex)
                    updatedSongs.add(intent.toIndex, song)
                    state.copy(songs = updatedSongs)
                }
            }
            is PlaylistIntent.ConfirmSort -> {
                viewModelScope.launch {
                    _effect.emit(PlaylistEffect.ShowToast("Playlist sorted"))
                }
            }
        }
    }
}
