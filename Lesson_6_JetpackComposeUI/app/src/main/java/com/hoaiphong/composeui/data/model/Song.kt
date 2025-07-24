package com.hoaiphong.composeui.data.model

import com.hoaiphong.composeui.R

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