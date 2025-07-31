package com.hoaiphong.composeui.ui.screen.mysong


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.data.model.Song

@Composable
fun MyListItem(
    song: Song,
    modifier: Modifier = Modifier.Companion,
    onRemoveClick: () -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(song.image),
            contentDescription = "",
            modifier = Modifier.Companion
                .size(70.dp)
                .padding(5.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        Spacer(modifier = Modifier.Companion.width(8.dp))

        Column(
            modifier = Modifier.Companion
                .weight(1f)
                .padding(end = 4.dp)
        ) {
            Text(
                song.name,
                fontWeight = FontWeight.Companion.Bold,
                fontSize = 20.sp,
                color = Color.Companion.White,
                maxLines = 1,
                overflow = TextOverflow.Companion.Ellipsis
            )
            Text(
                song.author,
                color = Color.Companion.LightGray,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Companion.Ellipsis
            )
        }
        Spacer(modifier = Modifier.Companion.width(8.dp))
        Row(
            verticalAlignment = Alignment.Companion.CenterVertically
        ) {
            Text(
                text = song.duration.toDurationFormatted(),
                fontSize = 20.sp,
                color = Color.Companion.White,
                maxLines = 1,
                overflow = TextOverflow.Companion.Ellipsis
            )

            IconButton(
                onClick = { expanded = true },
                modifier = Modifier.Companion.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_about),
                    contentDescription = "More Options",
                    tint = Color.Companion.White,
                    modifier = Modifier.Companion.size(18.dp)
                )
            }
            SongDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                onRemoveClick = onRemoveClick,
            )
        }
    }
}


@Composable
fun MyColumnItem(
    song: Song,
    modifier: Modifier = Modifier.Companion,
    onRemoveClick: () -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.Companion
                .size(135.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = rememberAsyncImagePainter(song.image),
                contentDescription = "",
                modifier = Modifier.Companion.matchParentSize(),
                contentScale = ContentScale.Companion.Crop
            )

            Box(
                modifier = Modifier.Companion
                    .align(Alignment.Companion.TopEnd)
                    .padding(8.dp)
            ) {
                IconButton(
                    onClick = { expanded = true },
                    modifier = Modifier.Companion
                        .background(Color.Companion.Black.copy(alpha = 0.6f), shape = CircleShape)
                        .size(32.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_about),
                        contentDescription = "",
                        modifier = Modifier.Companion.size(12.dp),
                        colorFilter = ColorFilter.Companion.tint(Color.Companion.White)
                    )
                }

                SongDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    onRemoveClick = onRemoveClick,
                )
            }
        }

        Spacer(modifier = Modifier.Companion.height(8.dp))

        Text(
            song.name,
            fontWeight = FontWeight.Companion.Bold,
            fontSize = 20.sp,
            color = Color.Companion.White,
            maxLines = 1,
            overflow = TextOverflow.Companion.Ellipsis

        )

        Spacer(modifier = Modifier.Companion.height(8.dp))

        Text(
            song.author,
            color = Color.Companion.Gray,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Companion.Ellipsis

        )

        Spacer(modifier = Modifier.Companion.height(8.dp))

        Text(
            text = song.duration.toDurationFormatted(),
            color = Color.Companion.White,
            fontSize = 20.sp,
            maxLines = 1,
            overflow = TextOverflow.Companion.Ellipsis
        )
    }
}


@Composable
fun GridSongList(
    songs: SnapshotStateList<Song>,
    onRemoveClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(songs.size) { index ->
            MyColumnItem(
                song = songs[index],
                onRemoveClick = { onRemoveClick(index) }
            )
        }
    }
}

@Composable
fun DropdownMenuItemShareDisabled() {
    DropdownMenuItem(
        text = {
            Text("Share (coming soon)", color = Color.Companion.Gray)
        },
        onClick = { },
        enabled = false,
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_about),
                contentDescription = null,
                modifier = Modifier.Companion.size(18.dp),
                colorFilter = ColorFilter.Companion.tint(Color.Companion.White)
            )
        }
    )
}


@Composable
fun ColumnSongList(
    songs: SnapshotStateList<Song>,
    onRemoveClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(songs.size) { index ->
            MyListItem(
                song = songs[index],
                onRemoveClick = { onRemoveClick(index) }
            )
        }
    }
}


@Composable
fun SongDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    Box(
        modifier = modifier.wrapContentSize(Alignment.Companion.TopStart)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            modifier = modifier
                .width(290.dp)
                .background(Color.Companion.Black.copy(alpha = 0.8f)),
            offset = DpOffset(x = -30.dp, y = 40.dp)
        ) {
            DropdownMenuItemRemove {
                onDismissRequest()
                onRemoveClick()
            }
            DropdownMenuItemShareDisabled()
        }
    }
}

@Composable
fun DropdownMenuItemRemove(onClick: () -> Unit) {
    DropdownMenuItem(
        text = {
            Text(
                "Remove from playlist",
                fontWeight = FontWeight.Companion.Bold,
                color = Color.Companion.White
            )
        },
        onClick = onClick,
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = null,
                modifier = Modifier.Companion.size(18.dp),
                colorFilter = ColorFilter.Companion.tint(Color.Companion.White)
            )
        }
    )
}

