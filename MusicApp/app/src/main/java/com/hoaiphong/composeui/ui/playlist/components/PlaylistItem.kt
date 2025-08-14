package com.hoaiphong.composeui.ui.playlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.data.local.fromBase64ToByteArray
import com.hoaiphong.composeui.data.local.model.entity.relations.PlaylistWithSongs
import com.hoaiphong.composeui.ui.playlist.MenuState

@Composable
fun PlaylistItem(
    playlistWithSongs: PlaylistWithSongs,
    modifier: Modifier = Modifier,
    showMenu: Boolean = true,
    onClick: () -> Unit = {},
    onRemoveClick: () -> Unit = {},
    onRename: (playlistId: Long, newName: String) -> Unit = { _, _ -> }
) {
    var menuState by remember { mutableStateOf<MenuState>(MenuState.None) }

    val playlist = playlistWithSongs.playlist
    val songs = playlistWithSongs.songs
    val imageBytes = songs.firstOrNull()?.image.fromBase64ToByteArray()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Companion.DarkGray)
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.Companion.CenterVertically) {
        // Thumbnail
        Box(
            modifier = Modifier.Companion
                .size(80.dp)
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = imageBytes?.let {
                        ImageRequest.Builder(LocalContext.current)
                            .data(it)
                            .build()
                    } ?: R.drawable.song1
                ),
                contentDescription = null,
                modifier = Modifier.Companion.matchParentSize(),
                contentScale = ContentScale.Companion.Crop
            )
        }

        Spacer(modifier = Modifier.Companion.width(12.dp))

        // Playlist name and song count
        Column(
            modifier = Modifier.Companion
                .weight(1f)
                .padding(end = if (showMenu) 8.dp else 0.dp)
        ) {
            Text(
                text = playlist.name ?: stringResource(R.string.untitled),
                fontWeight = FontWeight.Companion.Bold,
                fontSize = 18.sp,
                color = Color.Companion.White,
                maxLines = 1,
                overflow = TextOverflow.Companion.Ellipsis,
            )
            Text(
                text = "${songs.size} songs",
                color = Color.Companion.Gray,
                fontSize = 14.sp
            )
        }

        if (showMenu) {
            Box {
                IconButton(
                    onClick = { menuState = MenuState.Expanded }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_about),
                        contentDescription = null,
                        tint = Color.Companion.White,
                        modifier = Modifier.Companion.size(16.dp)
                    )
                }

                if (menuState is MenuState.Expanded) {
                    PlaylistDropdownMenu(
                        expanded = true,
                        onDismissRequest = { menuState = MenuState.None },
                        onRemoveClick = {
                            menuState = MenuState.None
                            onRemoveClick()
                        },
                        onRenameClick = {
                            menuState = MenuState.ShowRenameDialog
                        })
                }
            }
        }
    }

    if (showMenu && menuState is MenuState.ShowRenameDialog) {
        val currentName = playlist.name ?: ""
        RenamePlaylistDialog(
            currentName = currentName,
            onDismiss = { menuState = MenuState.None },
            onConfirm = { newName ->
                menuState = MenuState.None
                onRename(playlist.playlistId, newName)
            })
    }
}
