package com.hoaiphong.composeui.ui.playlistsong.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hoaiphong.composeui.comon.SongItem

@Composable
fun ColumnSongList(
    songs: List<SongItem>,
    onRemoveClick: (Int) -> Unit,
    onDropdownToggle: (Int) -> Unit,
    onDismissDropdown: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(songs.size) { index ->
            SongListItem(
                song = songs[index],
                onRemoveClick = { onRemoveClick(index) },
                onDropdownToggle = { onDropdownToggle(index) },
                onDismissDropdown = { onDismissDropdown(index) },
            )
        }
    }
}
