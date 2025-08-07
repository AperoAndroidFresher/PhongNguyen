package com.hoaiphong.composeui.ui.playlistsong.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.comon.SongItemState
import com.hoaiphong.composeui.utils.toDurationFormatted

@Composable
fun MyColumnItem(
    song: SongItemState,
    modifier: Modifier = Modifier.Companion,
    onRemoveClick: () -> Unit,
    onDropdownToggle: () -> Unit,
    onDismissDropdown: () -> Unit,
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.Companion
                .size(135.dp)
                .clip(RoundedCornerShape(10.dp)),
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
                modifier = Modifier.Companion.matchParentSize(),
                contentScale = ContentScale.Companion.Crop,
            )

            Box(
                modifier = Modifier.Companion
                    .align(Alignment.Companion.TopEnd)
                    .padding(8.dp),
            ) {
                IconButton(
                    onClick = onDropdownToggle,
                    modifier = Modifier.Companion
                        .background(Color.Companion.Black.copy(alpha = 0.6f), shape = CircleShape)
                        .size(32.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_about),
                        contentDescription = null,
                        modifier = Modifier.Companion.size(12.dp),
                        colorFilter = ColorFilter.Companion.tint(Color.Companion.White),
                    )
                }

                SongDropdownMenu(
                    expanded = song.isMenuExpanded,
                    onDismissRequest = onDismissDropdown,
                    onRemoveClick = onRemoveClick,
                )
            }
        }

        Spacer(modifier = Modifier.Companion.height(8.dp))

        Text(
            song.song.name,
            fontWeight = FontWeight.Companion.Bold,
            fontSize = 20.sp,
            color = Color.Companion.White,
            maxLines = 1,
            overflow = TextOverflow.Companion.Ellipsis,
        )

        Spacer(modifier = Modifier.Companion.height(8.dp))

        Text(
            song.song.author,
            color = Color.Companion.Gray,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Companion.Ellipsis,
        )

        Spacer(modifier = Modifier.Companion.height(8.dp))

        Text(
            text = song.song.duration.toDurationFormatted(),
            color = Color.Companion.White,
            fontSize = 20.sp,
            maxLines = 1,
            overflow = TextOverflow.Companion.Ellipsis,
        )
    }
}
