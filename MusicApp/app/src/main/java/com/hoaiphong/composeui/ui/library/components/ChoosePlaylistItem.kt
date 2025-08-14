package com.hoaiphong.composeui.ui.library.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.data.local.model.entity.relations.PlaylistWithSongs

@Composable
fun ChoosePlaylistItem(
    playlistWithSongs: PlaylistWithSongs,
    modifier: Modifier = Modifier,
) {
    val playlist = playlistWithSongs.playlist
    val songs = playlistWithSongs.songs

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Companion.DarkGray)
            .padding(8.dp),
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {
        Box(
            modifier = Modifier.Companion
                .size(80.dp)
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = songs.firstOrNull()?.image ?: R.drawable.ic_add_to_playlist
                ),
                contentDescription = null,
                modifier = Modifier.Companion.matchParentSize(),
                contentScale = ContentScale.Companion.Crop
            )
        }

        Spacer(modifier = Modifier.Companion.width(12.dp))

        Column(
            modifier = Modifier.Companion.weight(1f)
        ) {
            playlist.name?.let {
                Text(
                    it,
                    fontWeight = FontWeight.Companion.Bold,
                    fontSize = 18.sp,
                    color = Color.Companion.White,
                    maxLines = 1,
                    overflow = TextOverflow.Companion.Ellipsis
                )
            }
            Text(
                stringResource(R.string.songs, songs.size), color = Color.Companion.Gray, fontSize = 14.sp,
            )
        }
    }
}
