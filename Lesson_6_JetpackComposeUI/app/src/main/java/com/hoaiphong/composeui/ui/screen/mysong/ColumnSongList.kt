package com.hoaiphong.composeui.ui.screen.mysong

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import com.hoaiphong.composeui.data.model.Song

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