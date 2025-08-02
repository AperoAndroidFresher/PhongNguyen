package com.hoaiphong.composeui.ui.screen.library

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.data.model.Playlist
import com.hoaiphong.composeui.ui.screen.myalbum.MenuState
import com.hoaiphong.composeui.ui.screen.myalbum.PlaylistDropdownMenu
import com.hoaiphong.composeui.ui.screen.myalbum.PlaylistIntent
import com.hoaiphong.composeui.ui.screen.myalbum.PlaylistItem
import com.hoaiphong.composeui.ui.screen.myalbum.RenamePlaylistDialog

@Composable
fun ChoosePlaylistDialog(
    playlists: List<Playlist>,
    onDismiss: () -> Unit,
    onAddClick: () -> Unit,
    onPlaylistSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1E1E1E),
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Choose playlist",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            if (playlists.isEmpty()) {
                // Nếu không có playlist
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "You don't have any \nplaylists. Click the \n“+” button to add",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        IconButton(
                            onClick = onAddClick,
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
                    items(playlists.size) { index ->
                        val playlist = playlists[index]
                        ChoosePlaylistItem(
                            playlist = playlist,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.DarkGray, shape = RoundedCornerShape(10.dp))
                                .clickable { onPlaylistSelected(playlist.name) }
                        )
                    }
                }
            }
        },
        confirmButton = {}
    )
}
@Composable
fun ChoosePlaylistItem(
    playlist: Playlist,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.DarkGray)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Thumbnail
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = playlist.songs.firstOrNull()?.image ?: R.drawable.ic_add_to_playlist
                ),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Playlist name and song count
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                playlist.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                "${playlist.songs.size} songs",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}