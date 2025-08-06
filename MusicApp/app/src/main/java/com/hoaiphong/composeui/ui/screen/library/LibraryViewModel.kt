package com.hoaiphong.composeui.ui.screen.library

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hoaiphong.composeui.comon.SongItemState
import com.hoaiphong.composeui.data.model.PlaylistManager
import com.hoaiphong.composeui.data.model.Song
import com.hoaiphong.composeui.data.model.getAllMp3File
import com.hoaiphong.composeui.data.model.toEntity
import com.hoaiphong.composeui.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LibraryViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val playlistManager = PlaylistManager(
        playListDAO = db.playListDao(),
        songDAO = db.songDao(),
        crossRefDAO = db.playlistSongCrossRefDAO()
    )

    private val _uiState = MutableStateFlow(LibraryState())
    val uiState: StateFlow<LibraryState> = _uiState.asStateFlow()

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
        }
    }

    fun addSongToPlaylist(playlistId: Long, song: Song) {
        viewModelScope.launch {
            playlistManager.addSongToPlaylist(playlistId, song)
        }
    }

    private fun loadLocalSongs() {
        viewModelScope.launch(Dispatchers.IO) {
            val rawSongs = getAllMp3File(getApplication())
            val songEntities = rawSongs.map { it.toEntity() }
            db.songDao().insertAllSong(*songEntities.toTypedArray())

            val wrapped = rawSongs.map { SongItemState(it) }

            _uiState.update {
                it.copy(
                    songs = wrapped,
                    isLocalSelected = true
                )
            }
        }
    }

    private fun loadRemoteSongs() {
        _uiState.update {
            it.copy(
                songs = emptyList(),
                isLocalSelected = false
            )
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
                showAddToPlaylistDialog = true,
                selectedSongIndexForPlaylist = index
            )
        }
    }

    private fun dismissAddToPlaylistDialog() {
        _uiState.update {
            it.copy(
                showAddToPlaylistDialog = false,
                selectedSongIndexForPlaylist = null
            )
        }
    }
}
