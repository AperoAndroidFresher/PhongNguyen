package com.hoaiphong.composeui.ui.screen.mysong

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoaiphong.composeui.R
@Composable
fun PlaylistSongScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaylistViewModel = remember { PlaylistViewModel() }
) {
    val state by viewModel.uiState.collectAsState()

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
                        painter = painterResource(id = if (state.isColumnView) R.drawable.ic_column else R.drawable.ic_row),
                        contentDescription = "Toggle View",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                viewModel.onEvent(
                                    PlaylistIntent.ToggleView(!state.isColumnView)
                                )
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

            if (state.isColumnView) {
                ColumnSongList(
                    songs = state.songs.toMutableStateList(),
                    onRemoveClick = { index -> viewModel.onEvent(PlaylistIntent.RemoveSong(index)) }
                )
            } else {
                GridSongList(
                    songs = state.songs.toMutableStateList(),
                    onRemoveClick = { index -> viewModel.onEvent(PlaylistIntent.RemoveSong(index)) }
                )
            }
        }
    }
}