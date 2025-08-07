package com.hoaiphong.composeui.ui.library

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hoaiphong.composeui.comon.SongItemState
import com.hoaiphong.composeui.data.repository.impl.PlaylistManager
import com.hoaiphong.composeui.data.local.Song
import com.hoaiphong.composeui.data.local.getAllMp3File
import com.hoaiphong.composeui.data.local.toEntity
import com.hoaiphong.composeui.data.local.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import com.hoaiphong.composeui.api.song.SongAPIResponse
import com.hoaiphong.composeui.api.song.SongRetrofitClient
import kotlinx.coroutines.delay

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
                    songs = wrapped, isLocalSelected = true, hasNetworkError = false
                )
            }
        }
    }

    private fun loadRemoteSongs() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true, songs = emptyList(), isLocalSelected = false
                )
            }
            delay(2000)
            SongRetrofitClient.build().getSongApiResponse()
                .enqueue(object : Callback<List<SongAPIResponse>> {
                    override fun onResponse(
                        call: Call<List<SongAPIResponse>>, response: Response<List<SongAPIResponse>>
                    ) {
                        if (response.isSuccessful) {
                            val songs = response.body()?.map {
                                SongItemState(
                                    Song(
                                        id = 0,
                                        name = it.title,
                                        author = it.artist,
                                        duration = it.duration,
                                        image = null,
                                        data = it.path
                                    )
                                )
                            } ?: emptyList()

                            _uiState.update {
                                it.copy(songs = songs, isLoading = false, hasNetworkError = false)
                            }
                        } else {
                            Log.e("Retrofit", "Lỗi response: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<List<SongAPIResponse>>, t: Throwable) {
                        Log.e("Retrofit", "Lỗi mạng/API: ${t.message}")
                        _uiState.update {
                            it.copy(
                                isLoading = false, hasNetworkError = true
                            )
                        }
                    }
                })
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
