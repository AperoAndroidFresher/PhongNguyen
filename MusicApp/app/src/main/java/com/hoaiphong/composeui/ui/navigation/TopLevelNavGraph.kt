package com.hoaiphong.composeui.ui.navigation

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.hoaiphong.composeui.ui.home.HomeScreen
import com.hoaiphong.composeui.ui.layout.SharedBottom
import com.hoaiphong.composeui.ui.library.LibraryScreen
import com.hoaiphong.composeui.ui.playlist.PlaylistScreen
import com.hoaiphong.composeui.ui.information.InformationScreen
import com.hoaiphong.composeui.ui.playlistsong.PlaylistSongScreen
import com.hoaiphong.composeui.ui.playsong.PlayerState
import com.hoaiphong.composeui.ui.playsong.PlayerViewModel
import com.hoaiphong.composeui.ui.playsong.components.PlayingScreen
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.hoaiphong.composeui.service.MusicService

@Composable
fun TopLevelNavGraph(
    topLevelBackStack: TopLevelBackStack<Any>,
    onLogout: () -> Unit,
) {
    val context = LocalContext.current
    NavDisplay(
        backStack = topLevelBackStack.backStack,
        onBack = { topLevelBackStack.removeLast() },
        entryProvider = entryProvider {
            entry<Home> {
                SharedBottom(
                    selectedRoute = Home,
                    onNavigate = { target ->
                        when (target) {
                            is TopLevelRoute -> {
                                if (target != topLevelBackStack.topLevelKey) {
                                    topLevelBackStack.addTopLevel(target)
                                    topLevelBackStack.clear(target)
                                }
                            }

                            is MyInformationScreen -> {
                                topLevelBackStack.addTopLevel(target)
                            }
                        }
                    },
                    topLevelBackStack = topLevelBackStack
                ) { padding ->
                    HomeScreen(
                        modifier = Modifier.padding(padding),
                        onNavigate = { target -> handleNavigation(target, topLevelBackStack) }
                    )
                }
            }
            entry<MySong> {
                SharedBottom(
                    selectedRoute = MySong,
                    onNavigate = { target ->
                        if (target != topLevelBackStack.topLevelKey) {
                            topLevelBackStack.addTopLevel(target)
                            topLevelBackStack.clear(target)
                        }
                    },
                    topLevelBackStack = topLevelBackStack
                ) { padding ->
                    LibraryScreen(
                        modifier = Modifier.padding(padding),
                        onNavigateToPlaylistScreen = {
                            topLevelBackStack.addTopLevel(Song)
                        }
                    )
                }
            }
            entry<Song> {
                SharedBottom(
                    selectedRoute = Song,
                    onNavigate = { target ->
                        if (target != topLevelBackStack.topLevelKey) {
                            topLevelBackStack.addTopLevel(target)
                            topLevelBackStack.clear(target)
                        }
                    },
                    topLevelBackStack = topLevelBackStack
                ) { padding ->
                    PlaylistScreen(
                        modifier = Modifier.padding(padding),
                        onNavigateToPlaylistSongs = { playlistId ->
                            topLevelBackStack.addTopLevel(PlaylistSongs(playlistId))
                        }
                    )
                }
            }
            entry<MyInformationScreen> {
                InformationScreen(
                    onNavigateToLogin = {
                        topLevelBackStack.clear()
                        onLogout()
                    }
                )
            }
            entry<PlaylistSongs> { playlistSongs ->
                PlaylistSongScreen(entry = playlistSongs)
            }
            entry<PlayingSong> {
                val playerViewModel: PlayerViewModel = viewModel()
                val playerState by playerViewModel.uiState.collectAsState(initial = PlayerState())
                PlayingScreen(
                    playerState = playerState,
                    onPlayPauseToggle = { playerViewModel.togglePlayPause() },
                    onSeek = { pos -> playerViewModel.seekTo(pos) },
                    onBack = {
                        topLevelBackStack.clear(Home)
                    },
                    onClose = {
                        topLevelBackStack.clear(Home)
                        val stopIntent = Intent(context, MusicService::class.java).apply {
                            action = MusicService.ACTION_STOP
                        }
                        context.startService(stopIntent)
                    },
                    onPrevious = { playerViewModel.playPrevious() },
                    onNext = { playerViewModel.playNext() },
                    onShuffle = { playerViewModel.toggleShuffle() },
                    onRepeat = { playerViewModel.toggleRepeat() },
                )
            }
        },
    )
}

private fun handleNavigation(
    target: Any,
    topLevelBackStack: TopLevelBackStack<Any>
) {
    when (target) {
        is TopLevelRoute -> {
            if (target != topLevelBackStack.topLevelKey) {
                topLevelBackStack.addTopLevel(target)
            }
        }

        is MyInformationScreen -> {
            topLevelBackStack.addTopLevel(target)
        }
    }
}
