package com.hoaiphong.composeui.ui.playsong

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hoaiphong.composeui.service.MusicService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlayerViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(PlayerState())
    val uiState: StateFlow<PlayerState> = _uiState

    private var musicService: MusicService? = null
    private var serviceConnection: ServiceConnection? = null

    fun bindService(context: Context) {
        if (serviceConnection != null) return // tránh bind lại nhiều lần

        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                musicService = (service as MusicService.LocalBinder).getService()
                // Collect player state từ service để cập nhật UI
                viewModelScope.launch {
                    
                    musicService?.playerState?.collect {
                        _uiState.value = it
                    }
                }
            }
            override fun onServiceDisconnected(name: ComponentName?) {
                musicService = null
            }
        }
        context.bindService(
            Intent(context, MusicService::class.java),
            serviceConnection!!,
            Context.BIND_AUTO_CREATE
        )
    }

    fun unbindService(context: Context) {
        serviceConnection?.let {
            context.unbindService(it)
            serviceConnection = null
            musicService = null
        }
    }

    // Các hàm điều khiển
    fun togglePlayPause() {
        if (musicService == null) return
        if (musicService!!.isPlaying()) {
            musicService!!.pauseSong()
        } else {
            musicService!!.playCurrentSong()
        }
    }

    fun playNext() {
        musicService?.playNextSong()
    }

    fun playPrevious() {
        musicService?.playPreviousSong()
    }

    fun seekTo(positionMs: Long) {
        musicService?.seekTo(positionMs)
    }

    fun toggleShuffle() {
        musicService?.toggleShuffle()
    }

    fun toggleRepeat() {
        musicService?.toggleLoop()
    }
}
