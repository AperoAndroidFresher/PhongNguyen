package com.hoaiphong.composeui.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.hoaiphong.composeui.ui.home.HomeScreen
import com.hoaiphong.composeui.ui.layout.SharedBottom
import com.hoaiphong.composeui.ui.library.LibraryScreen
import com.hoaiphong.composeui.ui.playlist.PlaylistScreen
import com.hoaiphong.composeui.ui.myinfo.InformationScreen
import com.hoaiphong.composeui.ui.mysong.PlaylistSongScreen

@Composable
fun TopLevelNavGraph(
    topLevelBackStack: TopLevelBackStack<Any>
) {
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
                    }
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
                    }
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
                    }
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
                InformationScreen()
            }
            entry<PlaylistSongs> { playlistSongs ->
                PlaylistSongScreen(entry = playlistSongs)
            }
        }
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
