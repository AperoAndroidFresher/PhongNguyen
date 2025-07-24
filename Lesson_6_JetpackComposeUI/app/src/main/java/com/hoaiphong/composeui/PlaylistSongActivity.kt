package com.hoaiphong.composeui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class PlaylistSongActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_playlist_song)

    }
}
@Composable
fun SongDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.wrapContentSize(Alignment.TopStart)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            modifier = modifier.width(290.dp)
                .background(Color.Black.copy(alpha = 0.8f)),
            offset = DpOffset(x = -30.dp, y = 40.dp)
        ) {
            DropdownMenuItem(
                text = {
                    Text("Remove from playlist", fontWeight = FontWeight.Bold, color = Color.White)
                },
                onClick = {
                    onDismissRequest()
                    onRemoveClick()
                },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            )
            DropdownMenuItem(
                text = {
                    Text("Share (coming soon)", color = Color.Gray)
                },
                onClick = { },
                enabled = false,
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_about),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            )
        }
    }
}


@Composable
fun MyListItem(
    song: Song,
    modifier: Modifier = Modifier,
    onRemoveClick: () -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = song.imageResId),
            contentDescription = "",
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
                song.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                song.author,
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
                text = song.time,
                fontSize = 20.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            IconButton(
                onClick = { expanded = true },
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
                expanded = expanded,
                onDismissRequest = { expanded = false },
                onRemoveClick = onRemoveClick,
            )
        }
    }
}
@Composable
fun MyColumnItem(
    song: Song = Song(),
    modifier: Modifier = Modifier,
    onRemoveClick: () -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

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
                painter = painterResource(id = song.imageResId),
                contentDescription = "",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                IconButton(
                    onClick = { expanded = true },
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.6f), shape = CircleShape)
                        .size(32.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_about),
                        contentDescription = "",
                        modifier = Modifier.size(12.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }

                SongDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    onRemoveClick = onRemoveClick,
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            song.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis

        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            song.author,
            color = Color.Gray,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis

        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            song.time,
            color = Color.White,
            fontSize = 20.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

data class Song(
    val name: String = "Name of Song",
    val author: String = "Name of Author",
    val time: String = "Time of Song",
    val imageResId: Int = R.drawable.song1
)

val songList = listOf(
    Song("Song #1", "Author A", "3:10", R.drawable.song1),
    Song("Song #2", "Author B", "3:15", R.drawable.song2),
    Song("Song #3", "Author C", "4:05", R.drawable.song3),
    Song("Song #4", "Author D", "5:20", R.drawable.song4),
    Song("Song #5", "Author E", "4:45", R.drawable.song5),
    Song("Song #6", "Author F", "3:33", R.drawable.song1),
    Song("Song #7", "Author G", "5:12", R.drawable.song2),
    Song("Song #8", "Author H", "4:25", R.drawable.song3),
    Song("Song #9", "Author I", "3:50", R.drawable.song4),
    Song("Song #10", "Author J", "4:10", R.drawable.song5),
    Song("Song #11", "Author K", "3:18", R.drawable.song1),
    Song("Song #12", "Author L", "5:00", R.drawable.song2),
    Song("Song #13", "Author M", "4:35", R.drawable.song3),
    Song("Song #14", "Author N", "3:22", R.drawable.song4),
    Song("Song #15", "Author O", "5:45", R.drawable.song5),
    Song("Song #16", "Author P", "4:55", R.drawable.song1),
    Song("Song #17", "Author Q", "3:40", R.drawable.song2),
    Song("Song #18", "Author R", "4:15", R.drawable.song3),
    Song("Song #19", "Author S", "5:05", R.drawable.song4),
    Song("Song #20", "Author T", "4:42", R.drawable.song5)
)


@Preview(showBackground = true)
@Composable
fun PlaylistSong(modifier: Modifier = Modifier) {
    val songs = remember { mutableStateListOf<Song>().apply { addAll(songList) } }
    var isColumnView  by rememberSaveable { mutableStateOf(true) }


    Box(
        modifier = modifier
            .background(Color(0xFF121212))
            .fillMaxSize()
    ) {

        Column {
            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
            ) {
                Spacer(Modifier.padding(20.dp))
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
                        painter = painterResource(id = if (isColumnView) R.drawable.ic_column else R.drawable.ic_row),
                        contentDescription = "Toggle View",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                isColumnView = !isColumnView
                            }
                    )
                    Spacer(Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_sort_up),
                        contentDescription = "Sort Icon",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(25.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            if (isColumnView) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(songs.size) { index ->
                        MyListItem(
                            song = songs[index],
                            onRemoveClick = { songs.removeAt(index) },
                        )
                    }
                }
            }else
                LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(songs.size) { index ->
                    MyColumnItem(
                        song = songs[index],
                        onRemoveClick = { songs.removeAt(index) },
                    )
                }
            }

        }
    }
}