package com.hoaiphong.composeui.ui.playlistsong.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.comon.SongItemState

@Composable
fun GridSongList(
    songs: List<SongItemState>,
    onRemoveClick: (Int) -> Unit,
    onDropdownToggle: (Int) -> Unit,
    onDismissDropdown: (Int) -> Unit,
    modifier: Modifier = Modifier.Companion,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier.fillMaxSize(),
    ) {
        items(songs.size) { index ->
            MyColumnItem(
                song = songs[index],
                onRemoveClick = { onRemoveClick(index) },
                onDropdownToggle = { onDropdownToggle(index) },
                onDismissDropdown = { onDismissDropdown(index) },
            )
        }
    }
}
