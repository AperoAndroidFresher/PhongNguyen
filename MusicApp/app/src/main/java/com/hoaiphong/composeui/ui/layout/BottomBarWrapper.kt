package com.hoaiphong.composeui.ui.layout

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hoaiphong.composeui.service.MusicService
import com.hoaiphong.composeui.ui.library.LibraryIntent
import com.hoaiphong.composeui.ui.navigation.Home
import com.hoaiphong.composeui.ui.navigation.MySong
import com.hoaiphong.composeui.ui.navigation.Song
import com.hoaiphong.composeui.ui.navigation.TopLevelRoute
import com.hoaiphong.composeui.ui.playsong.PlayerViewModel
import com.hoaiphong.composeui.ui.playsong.components.MusicPlayer

@Composable
fun SharedBottom(
    selectedRoute: TopLevelRoute,
    onNavigate: (TopLevelRoute) -> Unit,
    playerViewModel: PlayerViewModel = viewModel(),
    content: @Composable (innerPadding: PaddingValues) -> Unit
) {
    val playerState by playerViewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        playerViewModel.bindService(context)
    }
    Scaffold(
        bottomBar = {
            Column {
                if (playerState.songName.isNotEmpty() && playerState.duration > 0) {
                    MusicPlayer(
                        currentTime = playerState.currentTime,
                        duration = playerState.duration,
                        songName = playerState.songName,
                        isPlaying = playerState.isPlaying,
                        onDeleteClick = {
                            val stopIntent = Intent(context, MusicService::class.java).apply {
                                action = MusicService.ACTION_STOP  
                            }
                            context.startService(stopIntent)
                        },
                        onPlayPauseClick = {
                            val playPauseIntent = Intent(context, MusicService::class.java).apply {
                                action = MusicService.ACTION_PLAY_PAUSE
                            }
                            context.startService(playPauseIntent)
                        }
                    )
                } else {
                    Spacer(modifier = Modifier.height(0.dp))
                }
                NavigationBar {
                    listOf(Home, MySong, Song).forEach { route ->
                        NavigationBarItem(
                            selected = route == selectedRoute,
                            onClick = { onNavigate(route) },
                            icon = {
                                Icon(imageVector = route.icon, contentDescription = null)
                            }
                        )
                    }
                }
            }
            
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}
