package com.hoaiphong.composeui.ui.screen.mysong

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.data.model.Song
import com.hoaiphong.composeui.data.model.songList

@Preview(showBackground = true)
@Composable
fun PlaylistSong(modifier: Modifier = Modifier) {
    val songs = remember { mutableStateListOf<Song>().apply { addAll(songList) } }
    var isColumnView  by rememberSaveable { mutableStateOf(true) }
    var isSorting  by rememberSaveable { mutableStateOf(true) }


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
                ColumnSongList(
                    songs = songs,
                    onRemoveClick = { index -> songs.removeAt(index) }
                )
            } else {
                GridSongList(
                    songs = songs,
                    onRemoveClick = { index -> songs.removeAt(index) }
                )
            }

        }
    }
}