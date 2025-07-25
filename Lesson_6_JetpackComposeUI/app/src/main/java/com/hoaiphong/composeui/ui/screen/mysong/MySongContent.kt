package com.hoaiphong.composeui.ui.screen.mysong

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hoaiphong.composeui.R

import com.hoaiphong.composeui.data.model.Song


@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewSongDropdownMenu() {
    SongDropdownMenu(
        expanded = true,
        onDismissRequest = {},
        onRemoveClick = {}
    )
}
@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewMyListItem() {
    val sampleSong = Song(
        name = "Sample Song",
        author = "Sample Artist",
        time = "3:45",
        imageResId = R.drawable.song1
    )

    MyListItem(
        song = sampleSong,
        onRemoveClick = {}
    )
}
@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewMyColumnItem() {
    val sampleSong = Song(
        name = "Sample Song",
        author = "Sample Artist",
        time = "4:20",
        imageResId = R.drawable.song1
    )

    MyColumnItem(
        song = sampleSong,
        onRemoveClick = {}
    )
}


