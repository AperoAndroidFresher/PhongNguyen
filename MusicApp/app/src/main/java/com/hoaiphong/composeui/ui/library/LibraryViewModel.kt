package com.hoaiphong.composeui.ui.library

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hoaiphong.composeui.comon.SongItem
import com.hoaiphong.composeui.data.repository.impl.PlaylistManager
import com.hoaiphong.composeui.data.local.SongLocal
import com.hoaiphong.composeui.data.local.getAllMp3File
import com.hoaiphong.composeui.data.local.toEntity
import com.hoaiphong.composeui.data.local.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.hoaiphong.composeui.data.internalstorage.SongStorage
import com.hoaiphong.composeui.data.internalstorage.Impl.SongStorageImpl
import com.hoaiphong.composeui.data.repository.impl.PlaylistRepositoryImpl

class LibraryViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val playlistManager = PlaylistRepositoryImpl(
        playListDAO = db.playListDao(),
        songDAO = db.songDao(),
        crossRefDAO = db.playlistSongCrossRefDAO()
    )
    private val songRepository: SongStorage = SongStorageImpl(application.applicationContext)

    private val _uiState = MutableStateFlow(LibraryState())
    val uiState: StateFlow<LibraryState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<LibraryEffect>()
    val effect = _effect.asSharedFlow()

    init {
        observePlaylists()
    }

    private fun observePlaylists() {
        viewModelScope.launch(Dispatchers.IO) {
            playlistManager.getPlaylistsWithSongs().collectLatest { playlists ->
                _uiState.update { it.copy(playlists = playlists) }
            }
        }
    }

    fun dispatch(intent: LibraryIntent) {
        when (intent) {
            is LibraryIntent.LoadLocalSongs -> loadLocalSongs()
            is LibraryIntent.LoadRemoteSongs -> loadRemoteSongs()
            is LibraryIntent.ToggleDropdown -> toggleDropdown(intent.index)
            is LibraryIntent.DismissDropdown -> dismissDropdown()
            is LibraryIntent.ShowAddToPlaylistDialog -> showAddToPlaylistDialog(intent.index)
            is LibraryIntent.DismissAddToPlaylistDialog -> dismissAddToPlaylistDialog()
            is LibraryIntent.LoadPlaylists -> {}
            is LibraryIntent.PlaySong -> playSong(intent.index)
        }
    }
    private fun playSong(index: Int) {
        val songItem = uiState.value.songs[index]
        val song = songItem.song
        val artist = song.author.ifEmpty { "Unknown Artist" }
        val image = song.image

        val playlistEntities = uiState.value.songs.map { it.song.toEntity() }
        viewModelScope.launch {
            _effect.emit(
                LibraryEffect.StartMusicService(
                    playlist = playlistEntities,
                    startIndex = index,
                    artist = artist,
                    image = image
                )
            )
        }
    }
    fun addSongToPlaylist(playlistId: Long, song: SongLocal) {
        viewModelScope.launch {
            playlistManager.addSongToPlaylist(playlistId, song)
        }
    }

    private fun loadLocalSongs() {
        viewModelScope.launch(Dispatchers.IO) {
            val rawSongs = getAllMp3File(getApplication())
            val songEntities = rawSongs.map { it.toEntity() }
            db.songDao().insertAllSong(*songEntities.toTypedArray())

            val wrapped = rawSongs.map { SongItem(it) }

            _uiState.update {
                it.copy(
                    songs = wrapped, isLocalSelected = true, hasNetworkError = false
                )
            }
        }
    }


    fun loadRemoteSongs() {
        _uiState.update {
            it.copy(
                isLoading = true,
                songs = emptyList(),
                isLocalSelected = false,
                hasNetworkError = false
            )
        }

        songRepository.loadRemoteSongsAndDownloadFiles { songStates ->
            if (songStates != null && songStates.isNotEmpty()) {
                _uiState.update {
                    it.copy(
                        songs = songStates,
                        isLoading = false,
                        hasNetworkError = false
                    )
                }
            } else if (songStates != null && songStates.isEmpty()) {
                _uiState.update {
                    it.copy(
                        songs = emptyList(),
                        isLoading = false,
                        hasNetworkError = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        hasNetworkError = true
                    )
                }
            }
        }
    }
    private fun toggleDropdown(index: Int) {
        _uiState.update {
            val updated = it.songs.mapIndexed { i, item ->
                if (i == index) item.copy(isMenuExpanded = !item.isMenuExpanded)
                else item.copy(isMenuExpanded = false)
            }
            it.copy(songs = updated)
        }
    }

    private fun dismissDropdown() {
        _uiState.update {
            val updated = it.songs.map { it.copy(isMenuExpanded = false) }
            it.copy(songs = updated)
        }
    }

    private fun showAddToPlaylistDialog(index: Int) {
        _uiState.update {
            it.copy(
                showAddToPlaylistDialog = true, selectedSongIndexForPlaylist = index
            )
        }
    }

    private fun dismissAddToPlaylistDialog() {
        _uiState.update {
            it.copy(
                showAddToPlaylistDialog = false, selectedSongIndexForPlaylist = null
            )
        }
    }
}
