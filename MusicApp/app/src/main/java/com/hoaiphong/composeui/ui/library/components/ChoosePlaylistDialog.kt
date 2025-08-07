package com.hoaiphong.composeui.ui.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.data.local.model.entity.relations.PlaylistWithSongs

@Composable
fun ChoosePlaylistDialog(
    playlists: List<PlaylistWithSongs>,
    onDismiss: () -> Unit,
    onAddClick: () -> Unit,
    onPlaylistSelected: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismiss, containerColor = Color(0xFF1E1E1E), title = {
            Box(
                modifier = Modifier.Companion.fillMaxWidth(),
                contentAlignment = Alignment.Companion.Center
            ) {
                Text(
                    text = "Choose playlist",
                    fontWeight = FontWeight.Companion.Bold,
                    color = Color.Companion.White,
                    textAlign = TextAlign.Companion.Center
                )
            }
        }, text = {
            if (playlists.isEmpty()) {
                Box(
                    modifier = Modifier.Companion
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Companion.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.Companion.CenterHorizontally
                    ) {
                        Text(
                            text = "You don't have any \nplaylists. Click the \n“+” button to add",
                            color = Color.Companion.White,
                            textAlign = TextAlign.Companion.Center
                        )
                        Spacer(modifier = Modifier.Companion.height(16.dp))
                        IconButton(
                            onClick = onAddClick,
                            modifier = Modifier.Companion
                                .size(48.dp)
                                .background(
                                    Color.Companion.Transparent,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .border(
                                    1.dp,
                                    Color.Companion.White,
                                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add playlist",
                                tint = Color.Companion.White
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(playlists) { playlistWithSongs ->
                        ChoosePlaylistItem(
                            playlistWithSongs = playlistWithSongs,
                            modifier = Modifier.Companion
                                .fillMaxWidth()
                                .clickable {
                                    onPlaylistSelected(playlistWithSongs.playlist.playlistId)
                                })
                    }
                }
            }
        }, confirmButton = {}, modifier = modifier
    )
}
