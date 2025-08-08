package com.hoaiphong.composeui.ui.playsong

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoaiphong.composeui.service.MusicService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlayerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PlayerState())
    val uiState: StateFlow<PlayerState> = _uiState

    fun bindService(context: Context) {
        val serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val musicService = (service as MusicService.LocalBinder).getService()
                viewModelScope.launch {
                    musicService.playerState.collect {
                        _uiState.value = it
                    }
                }
            }
            override fun onServiceDisconnected(name: ComponentName?) {}
        }
        context.bindService(Intent(context, MusicService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)
    }
}
