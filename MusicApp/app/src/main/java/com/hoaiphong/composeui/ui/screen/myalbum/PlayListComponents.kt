package com.hoaiphong.composeui.ui.screen.myalbum

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.db.entity.Playlist
import com.hoaiphong.composeui.db.entity.relations.PlaylistWithSongs
import com.hoaiphong.composeui.ui.screen.mysong.DropdownMenuItemRemove
import com.hoaiphong.composeui.ui.screen.mysong.DropdownMenuItemShare
import com.hoaiphong.composeui.ui.screen.mysong.SongDropdownMenu

@Composable
fun PlaylistDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onRemoveClick: () -> Unit,
    onRenameClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.wrapContentSize(Alignment.TopStart)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            modifier = modifier
                .width(290.dp)
                .background(Color.Black.copy(alpha = 0.8f)),
            offset = DpOffset(x = 50.dp, y = 200.dp)
        ) {
            DropdownMenuItemRemove {
                onDismissRequest()
                onRemoveClick()
            }
            DropdownMenuItemRename(onClick = {
                onDismissRequest()
                onRenameClick()
            })
        }
    }
}

@Composable
fun DropdownMenuItemRename(onClick: () -> Unit) {
    DropdownMenuItem(
        text = { Text("Rename", color = Color.White) },
        onClick = onClick,
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_edit_playlist),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    )
}
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

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.DarkGray)
            .clickable { onClick() }
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
                    model = songs.firstOrNull()?.image ?: R.drawable.ic_add_to_playlist
                ),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Playlist name and song count
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = if (showMenu) 8.dp else 0.dp)
        ) {
            Text(
                text = playlist.name ?: "Untitled",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${songs.size} songs",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        if (showMenu) {
            Box {
                IconButton(
                    onClick = { menuState = MenuState.Expanded }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_about),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
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
                        }
                    )
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
            }
        )
    }
}

@Composable
fun RenamePlaylistDialog(
    currentName: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf(currentName) }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Text(
                "OK",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onConfirm(text) },
                color = Color.White
            )
        },
        dismissButton = {
            Text(
                "Cancel",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onDismiss() },
                color = Color.Gray
            )
        },
        title = { Text("Rename Playlist", color = Color.White) },
        text = {
            androidx.compose.material3.TextField(
                value = text,
                onValueChange = { text = it },
                singleLine = true
            )
        },
        containerColor = Color.DarkGray
    )
}