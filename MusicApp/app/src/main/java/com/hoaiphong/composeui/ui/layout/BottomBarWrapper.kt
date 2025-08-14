package com.hoaiphong.composeui.ui.layout

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hoaiphong.composeui.service.MusicService
import com.hoaiphong.composeui.ui.navigation.Home
import com.hoaiphong.composeui.ui.navigation.Library
import com.hoaiphong.composeui.ui.navigation.PlayingSong    
import com.hoaiphong.composeui.ui.navigation.Playlist
import com.hoaiphong.composeui.ui.navigation.TopLevelBackStack  
import com.hoaiphong.composeui.ui.navigation.TopLevelRoute
import com.hoaiphong.composeui.ui.playsong.PlayerViewModel
import com.hoaiphong.composeui.ui.playsong.components.MusicPlayer

@Composable
fun SharedBottom(
    selectedRoute: TopLevelRoute,
    onNavigate: (TopLevelRoute) -> Unit,
    topLevelBackStack: TopLevelBackStack<Any>,   
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
                        },
                        onNavigateToPlayingScreen = {
                            topLevelBackStack.addTopLevel(PlayingSong)
                        }
                    )
                } else {
                    Spacer(modifier = Modifier.height(0.dp))
                }
                NavigationBar {
                    listOf(Home, Library, Playlist).forEach { route ->
                        NavigationBarItem(
                            selected = route == selectedRoute,
                            onClick = { onNavigate(route) },
                            icon = {
                                Image(
                                    painter = painterResource(id = route.iconRes),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                            },
                            label = {
                                Text(text = route.label, color = Color.White)
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
