package com.hoaiphong.composeui.ui.screen.mysong

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