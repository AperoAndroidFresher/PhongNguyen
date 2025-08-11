package com.hoaiphong.composeui.ui.playsong.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.ui.playsong.PlayerState
import com.hoaiphong.composeui.ui.playsong.PlayerViewModel
import com.hoaiphong.composeui.utils.formatTime

@Composable
fun MusicPlayerContent(
    playerState: PlayerState,
    onDeleteClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNavigateToPlayingScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = if (playerState.duration > 0) playerState.currentTime.toFloat() / playerState.duration else 0f

    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = "Delete",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = modifier.size(16.dp)
                        .clickable(onClick = onDeleteClick)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            MusicProgressBar(progress = progress)

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF292929))
                    .clickable { onNavigateToPlayingScreen() }
                    .padding(8.dp),
            ) {
                Icon(
                    painter = painterResource(
                        id = if (playerState.isPlaying) R.drawable.ic_play else R.drawable.ic_pause
                    ),
                    contentDescription = if (playerState.isPlaying) "Pause" else "Play",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = onPlayPauseClick),
                    tint = Color.Unspecified,
                )
                Text(
                    text = playerState.songName.ifEmpty { "Unknown Song" },
                    color = Color.White
                )
                Text(
                    text = formatTime(playerState.currentTime),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun MusicPlayer(
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit = {},
    onPlayPauseClick: () -> Unit = {},
    onNavigateToPlayingScreen: () -> Unit = {},
) {
    val playerViewModel: PlayerViewModel = viewModel()
    val playerState by playerViewModel.uiState.collectAsState(initial = PlayerState())

    MusicPlayerContent(
        playerState = playerState,
        onDeleteClick = onDeleteClick,
        onPlayPauseClick = onPlayPauseClick,
        onNavigateToPlayingScreen = onNavigateToPlayingScreen,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun MusicPlayerPreview() {
    val samplePlayerState = PlayerState(
        songName = "Sample Song",
        currentTime = 30_000L,
        duration = 180_000L,
        isPlaying = true,
    )
    MusicPlayerContent(
        playerState = samplePlayerState,
        onDeleteClick = {},
        onPlayPauseClick = {},
        onNavigateToPlayingScreen = {},
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}
