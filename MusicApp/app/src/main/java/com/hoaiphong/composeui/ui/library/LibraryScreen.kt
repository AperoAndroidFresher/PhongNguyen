package com.hoaiphong.composeui.ui.library

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hoaiphong.composeui.data.local.toEntity
import com.hoaiphong.composeui.service.MusicService
import com.hoaiphong.composeui.ui.library.components.ChoosePlaylistDialog
import com.hoaiphong.composeui.ui.library.components.LibraryButton
import com.hoaiphong.composeui.ui.library.components.LottieAnimationLoading
import com.hoaiphong.composeui.ui.library.components.NoInternetConnectionContent
import com.hoaiphong.composeui.ui.playlistsong.components.SongPlayListItem

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = viewModel(),
    onNavigateToPlaylistScreen: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.dispatch(LibraryIntent.LoadLocalSongs)
        viewModel.dispatch(LibraryIntent.LoadPlaylists)
    }
    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is LibraryEffect.StartMusicService -> {
                    val intent = Intent(context, MusicService::class.java).apply {
                        action = MusicService.ACTION_PLAY_PLAYLIST
                        putParcelableArrayListExtra("playlist", ArrayList(effect.playlist))
                        putExtra("startIndex", effect.startIndex)
                    }
                    context.startService(intent)
                }
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Library", fontSize = 25.sp, color = Color.White, textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            LibraryButton(
                text = "Local",
                modifier = Modifier.width(100.dp),
                onClick = { viewModel.dispatch(LibraryIntent.LoadLocalSongs) })

            Spacer(modifier = Modifier.width(20.dp))

            LibraryButton(
                text = "Remote",
                modifier = Modifier.width(100.dp),
                onClick = { viewModel.dispatch(LibraryIntent.LoadRemoteSongs) })
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x88000000)),
                contentAlignment = Alignment.Center
            ) {
                LottieAnimationLoading()
            }
        } else if (state.hasNetworkError) {
            NoInternetConnectionContent(
                onRetry = { viewModel.dispatch(LibraryIntent.LoadRemoteSongs) })
        } else {
            LazyColumn {
                itemsIndexed(state.songs) { index, songState ->
                    SongPlayListItem(
                        song = songState,
                        onItemClick = {
                            viewModel.dispatch(LibraryIntent.PlaySong(index))
                        },
                        onAddClick = {
                            viewModel.dispatch(LibraryIntent.ShowAddToPlaylistDialog(index))
                        },
                        onDropdownToggle = {
                            viewModel.dispatch(LibraryIntent.ToggleDropdown(index))
                        },
                        onDismissDropdown = {
                            viewModel.dispatch(LibraryIntent.DismissDropdown)
                        }
                    )
                }
            }
        }

        if (state.showAddToPlaylistDialog) {
            ChoosePlaylistDialog(
                playlists = state.playlists,
                onDismiss = { viewModel.dispatch(LibraryIntent.DismissAddToPlaylistDialog) },
                onAddClick = {
                    viewModel.dispatch(LibraryIntent.DismissAddToPlaylistDialog)
                    onNavigateToPlaylistScreen()
                },
                onPlaylistSelected = { playlistId ->
                    state.selectedSongIndexForPlaylist?.let { index ->
                        val song = state.songs[index].song
                        viewModel.addSongToPlaylist(playlistId, song)
                        viewModel.dispatch(LibraryIntent.DismissAddToPlaylistDialog)
                    }
                })
        }
    }
}
