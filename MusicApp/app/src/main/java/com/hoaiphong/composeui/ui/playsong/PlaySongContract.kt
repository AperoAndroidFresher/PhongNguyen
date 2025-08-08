package com.hoaiphong.composeui.ui.playsong

data class PlayerState(
    val songName: String = "",
    val currentTime: Long = 0,
    val duration: Long = 0,
    val isPlaying: Boolean = false
)
