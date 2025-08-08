package com.hoaiphong.composeui.ui.playsong.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.utils.formatTime

@Preview(showBackground = true)
@Composable
fun MusicPlayer(
    modifier: Modifier = Modifier,
    currentTime: Long = 0,
    duration: Long = 0,
    songName: String = "meo meo meo meo",
    isPlaying: Boolean = false,
    onDeleteClick: () -> Unit = {},
    onPlayPauseClick: () -> Unit = {},
) {
    val progress = if (duration > 0) currentTime.toFloat() / duration else 0f
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            // Delete icon
            Image(
                painter = painterResource(R.drawable.ic_delete),
                contentDescription = "Delete",
                colorFilter = ColorFilter.tint(Color.White),
                alignment = Alignment.CenterEnd,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(16.dp)
                    .padding(end = 8.dp)
                    .clickable(onClick = onDeleteClick)  
            )


            Spacer(modifier = Modifier.height(8.dp))
            MusicProgressBar(progress = progress)

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF292929))
                    .padding(8.dp),
            ) {
                // Play/Pause icon
                Icon(
                    painter = painterResource(
                        id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
                    ),
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = onPlayPauseClick),
                    tint = Color.Unspecified,
                )
                Text(
                    text = songName,
                    color = Color.White
                )
                Text(
                    text = formatTime(currentTime),
                    color = Color.White
                )
            }
        }
    }
}
