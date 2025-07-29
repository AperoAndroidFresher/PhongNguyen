package com.hoaiphong.composeui.ui.screen.mysong

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.data.model.Song

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