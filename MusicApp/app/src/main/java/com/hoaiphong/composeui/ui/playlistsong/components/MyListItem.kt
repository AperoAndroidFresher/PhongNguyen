package com.hoaiphong.composeui.ui.playlistsong.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.comon.SongItem
import com.hoaiphong.composeui.ui.components.SongDropdownMenu
import com.hoaiphong.composeui.utils.toDurationFormatted

@Composable
fun SongListItem(
    song: SongItem,
    modifier: Modifier = Modifier,
    onRemoveClick: () -> Unit,
    onDropdownToggle: () -> Unit,
    onDismissDropdown: () -> Unit,
    onPlayClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Companion.CenterVertically,
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = song.song.image?.let {
                    ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .build()
                } ?: R.drawable.song1,
            ),
            contentDescription = null,
            modifier = Modifier.Companion
                .size(70.dp)
                .padding(5.dp)
                .clip(RoundedCornerShape(10.dp)),
        )

        Spacer(modifier = Modifier.Companion.width(8.dp))

        Column(
            modifier = Modifier.Companion
                .weight(1f)
                .padding(end = 4.dp)
                .clickable { onPlayClick() } ,
        ) {
            Text(
                text = song.song.name,
                fontWeight = FontWeight.Companion.Bold,
                fontSize = 20.sp,
                color = Color.Companion.White,
                maxLines = 1,
                overflow = TextOverflow.Companion.Ellipsis,
            )
            Text(
                text = song.song.author,
                color = Color.Companion.LightGray,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Companion.Ellipsis,
            )
        }

        Spacer(modifier = Modifier.Companion.width(8.dp))

        Row(
            verticalAlignment = Alignment.Companion.CenterVertically,
        ) {
            Text(
                text = song.song.duration.toDurationFormatted(),
                fontSize = 20.sp,
                color = Color.Companion.White,
                maxLines = 1,
                overflow = TextOverflow.Companion.Ellipsis,
            )

            IconButton(
                onClick = onDropdownToggle,
                modifier = Modifier.Companion.size(24.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_about),
                    contentDescription = "More Options",
                    tint = Color.Companion.White,
                    modifier = Modifier.Companion.size(18.dp),
                )
            }

            SongDropdownMenu(
                expanded = song.isMenuExpanded,
                onDismissRequest = onDismissDropdown,
                onRemoveClick = onRemoveClick,
            )
        }
    }
}
