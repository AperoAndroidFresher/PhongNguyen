package com.hoaiphong.composeui.ui.playlistsong


import android.content.Intent
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.comon.SongItemState

@Composable
fun MyListItem(
    song: SongItemState,
    modifier: Modifier = Modifier,
    onRemoveClick: () -> Unit,
    onDropdownToggle: () -> Unit,
    onDismissDropdown: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter( model = song.song.image?.let {
                ImageRequest.Builder(LocalContext.current)
                    .data(it)
                    .build()
            } ?: R.drawable.song1),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
                .padding(5.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        ) {
            Text(
                text = song.song.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = song.song.author,
                color = Color.LightGray,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = song.song.duration.toDurationFormatted(),
                fontSize = 20.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            IconButton(
                onClick = onDropdownToggle,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_about),
                    contentDescription = "More Options",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }

            SongDropdownMenu(
                expanded = song.isMenuExpanded,
                onDismissRequest = onDismissDropdown,
                onRemoveClick = onRemoveClick
            )
        }
    }
}
@Composable
fun MyColumnItem(
    song: SongItemState,
    modifier: Modifier = Modifier,
    onRemoveClick: () -> Unit,
    onDropdownToggle: () -> Unit,
    onDismissDropdown: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(135.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = rememberAsyncImagePainter( model = song.song.image?.let {
                    ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .build()
                } ?: R.drawable.song1),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                IconButton(
                    onClick = onDropdownToggle,
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.6f), shape = CircleShape)
                        .size(32.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_about),
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }

                SongDropdownMenu(
                    expanded = song.isMenuExpanded,
                    onDismissRequest = onDismissDropdown,
                    onRemoveClick = onRemoveClick,
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            song.song.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            song.song.author,
            color = Color.Gray,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = song.song.duration.toDurationFormatted(),
            color = Color.White,
            fontSize = 20.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
@Composable
fun GridSongList(
    songs: List<SongItemState>,
    onRemoveClick: (Int) -> Unit,
    onDropdownToggle: (Int) -> Unit,
    onDismissDropdown: (Int) -> Unit,
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
                onRemoveClick = { onRemoveClick(index) },
                onDropdownToggle = { onDropdownToggle(index) },
                onDismissDropdown = { onDismissDropdown(index) }
            )
        }
    }
}

@Composable
fun ColumnSongList(
    songs: List<SongItemState>,
    onRemoveClick: (Int) -> Unit,
    onDropdownToggle: (Int) -> Unit,
    onDismissDropdown: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(songs.size) { index ->
            MyListItem(
                song = songs[index],
                onRemoveClick = { onRemoveClick(index) },
                onDropdownToggle = { onDropdownToggle(index) },
                onDismissDropdown = { onDismissDropdown(index) }
            )
        }
    }
}

@Composable
fun DropdownMenuItemShare() {
    val context = LocalContext.current

    DropdownMenuItem(
        text = { Text("Share", color = Color.White) },
        onClick = {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "audio/*"
            }
            context.startActivity(Intent.createChooser(intent, "Share with"))
        },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    )
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
            DropdownMenuItemShare()
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

@Composable
fun DropdownMenuItemAdd(onClick: () -> Unit) {
    DropdownMenuItem(
        text = {
            Text(
                "Add to playlist",
                fontWeight = FontWeight.Companion.Bold,
                color = Color.Companion.White
            )
        },
        onClick = onClick,
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_add_to_playlist),
                contentDescription = null,
                modifier = Modifier.Companion.size(18.dp),
                colorFilter = ColorFilter.Companion.tint(Color.Companion.White)
            )
        }
    )
}
@Composable
fun PlaylistDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onAddClick: () -> Unit,
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
            offset = DpOffset(x = -30.dp, y = 10.dp)
        ) {
            DropdownMenuItemAdd {
                onDismissRequest()
                onAddClick()
            }
            DropdownMenuItemShare()
        }
    }
}

@Composable
fun MyPlayListItem(
    song: SongItemState,
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
    onDropdownToggle: () -> Unit,
    onDismissDropdown: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter( model = song.song.image?.let {
                ImageRequest.Builder(LocalContext.current)
                    .data(it)
                    .build()
            } ?: R.drawable.song1),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
                .padding(5.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        ) {
            Text(
                text = song.song.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = song.song.author,
                color = Color.LightGray,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = song.song.duration.toDurationFormatted(),
                fontSize = 20.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            IconButton(
                onClick = onDropdownToggle,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_about),
                    contentDescription = "More Options",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }

            PlaylistDropdownMenu(
                expanded = song.isMenuExpanded,
                onDismissRequest = onDismissDropdown,
                onAddClick = onAddClick
            )
        }
    }
}
