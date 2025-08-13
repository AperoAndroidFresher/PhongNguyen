package com.hoaiphong.composeui.ui.playlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.comon.UserSession
import com.hoaiphong.composeui.ui.playlist.components.PlaylistItem

@Composable
fun PlaylistScreen(
    modifier: Modifier = Modifier,
    viewModel: PlayListViewModel = viewModel(),
    onNavigateToPlaylistSongs: (Long) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    var newPlaylistName by rememberSaveable { mutableStateOf("") }
    val userSession = remember { UserSession(context) }

    Box(
        modifier = modifier
            .background(Color(0xFF121212))
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(8.dp)
    ) {
        Column {

            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
            ) {
                Text(
                    text = "My Playlist",
                    fontSize = 25.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )

                Row(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "Add Playlist",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                viewModel.dispatch(PlaylistIntent.ShowAddPlaylistDialog)
                            })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (state.playlists.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Choose playlist",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                            text = "You don't have any \nplaylists. Click the \n“+” button to add",
                            fontSize = 16.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        IconButton(
                            onClick = {
                                viewModel.dispatch(PlaylistIntent.ShowAddPlaylistDialog)
                            },
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color.Transparent, shape = RoundedCornerShape(12.dp))
                                .border(1.dp, Color.White, shape = RoundedCornerShape(12.dp))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add playlist",
                                tint = Color.White
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(state.playlists) { index, playlistWithCount ->
                        val playlist = playlistWithCount.playlist

                        PlaylistItem(
                            playlistWithSongs = playlistWithCount,
                            onClick = {
                                onNavigateToPlaylistSongs(playlist.playlistId)
                            },
                            onRemoveClick = {
                                viewModel.dispatch(PlaylistIntent.RemovePlaylist(playlist.playlistId))
                            },
                            onRename = { playlistId, newName ->
                                viewModel.dispatch(
                                    PlaylistIntent.RenamePlaylist(
                                        playlistId,
                                        newName
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }

        // Dialog thêm playlist
        if (state.showAddDialog) {
            AlertDialog(onDismissRequest = {
                viewModel.dispatch(PlaylistIntent.DismissAddPlaylistDialog)
                newPlaylistName = ""
            }, title = { Text(text = "New Playlist") }, text = {
                OutlinedTextField(
                    value = newPlaylistName,
                    onValueChange = { newPlaylistName = it },
                    label = { Text("Enter name") })
            }, confirmButton = {
                Button(
                    onClick = {
                        val username = userSession.getSavedUsername()
                        if (username.isNullOrBlank()) return@Button

                        viewModel.dispatch(
                            PlaylistIntent.AddPlaylist(
                                name = newPlaylistName,
                                ownerUsername = username
                            )
                        )
                        newPlaylistName = ""
                    }
                ) {
                    Text("Add")
                }
            }, dismissButton = {
                Button(
                    onClick = {
                        viewModel.dispatch(PlaylistIntent.DismissAddPlaylistDialog)
                        newPlaylistName = ""
                    }) {
                    Text("Cancel")
                }
            })
        }
    }
}
