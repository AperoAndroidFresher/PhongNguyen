package com.hoaiphong.composeui.utils

fun String.toDurationFormatted(): String {
    return try {
        val durationMs = this.toLong()
        val minutes = (durationMs / 1000) / 60
        val seconds = (durationMs / 1000) % 60
        String.format("%d:%02d", minutes, seconds)
    } catch (e: NumberFormatException) {
        "0:00"
    }
}

fun formatTime(milliseconds: Long): String {
    val totalSeconds = milliseconds / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}
