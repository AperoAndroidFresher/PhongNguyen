package com.hoaiphong.composeui.ui.screen.myalbum

import androidx.lifecycle.ViewModel
import com.hoaiphong.composeui.data.model.PlaylistManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class PlayListViewModel: ViewModel() {
    private val _state = MutableStateFlow(PlayListState())
    val state: StateFlow<PlayListState> = _state.asStateFlow()

    fun dispatch(intent: PlaylistIntent){
        when(intent){
            is PlaylistIntent.AddPlaylist -> {
                if(intent.name.isNotBlank()){
                    PlaylistManager.addPlaylist(intent.name.trim())
                    val updateList = PlaylistManager.getPlaylists()
                    _state.update {
                        it.copy(
                            playlists = updateList,
                            showAddDialog = false
                        )
                    }
                }
            }
            PlaylistIntent.DismissAddPlaylistDialog -> {
                _state.update { it.copy(showAddDialog = false) }
            }
            PlaylistIntent.ShowAddPlaylistDialog -> {
                _state.update { it.copy(showAddDialog = true) }
            }
        }
    }
}