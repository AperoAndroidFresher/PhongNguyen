package com.hoaiphong.composeui.ui.playsong.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.data.local.fromBase64ToByteArray
import com.hoaiphong.composeui.ui.playsong.PlayerState
import com.hoaiphong.composeui.utils.formatTime

@Preview(showBackground = true)
@Composable
fun PlayingScreenPreview() {
    val samplePlayerState = PlayerState(
        songName = "grainy days",
        artistName = "moody.",
        image = "", 
        currentTime = 127_000L, 
        duration = 163_000L,    
        isPlaying = false,
    )

    PlayingScreen(
        playerState = samplePlayerState,
        onPlayPauseToggle = {},
        onSeek = {},
        onBack = {},
        onClose = {},
        onPrevious = {},
        onNext = {},
        onShuffle = {},
        onRepeat = {},
    )
}

@Composable
fun PlayingScreen(
    playerState: PlayerState,
    onPlayPauseToggle: () -> Unit,
    onSeek: (Long) -> Unit,
    onBack: () -> Unit,
    onClose: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onShuffle: () -> Unit,
    onRepeat: () -> Unit,
) {
    val duration = playerState.duration.toFloat()
    var sliderPosition by remember { mutableFloatStateOf(playerState.currentTime.toFloat()) }
    val imageByteArray = remember(playerState.image) {
        playerState.image.fromBase64ToByteArray()
    }
    val painterModel = if (imageByteArray?.isNotEmpty() == true) imageByteArray else R.drawable.song1
    LaunchedEffect(playerState.currentTime) {
        sliderPosition = playerState.currentTime.toFloat()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF292929))
            .padding(16.dp),
    ) {
        // Top Bar
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.clickable { onBack() }
            )
            Text("Now Playing", color = Color.White, fontWeight = FontWeight.Bold)
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.White,
                modifier = Modifier.clickable { onClose() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Album Art
        Image(
            painter = rememberAsyncImagePainter(
                model = painterModel
            ),
            contentDescription = "Album Art",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Song Title & Artist
        Text(
            playerState.songName.ifEmpty { "Unknown Song" },
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            playerState.artistName.ifEmpty { "Unknown Artist" },
            color = Color.Gray,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Progress Slider
        Slider(
            value = sliderPosition.coerceIn(0f, duration),
            onValueChange = {
                sliderPosition = it
            },
            onValueChangeFinished = {
                onSeek(sliderPosition.toLong())
            },
            valueRange = 0f..duration,
            colors = SliderDefaults.colors(
                thumbColor = Color.Cyan,
                activeTrackColor = Color.Cyan,
                inactiveTrackColor = Color.Gray,
            ),
        )

        // Time
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(formatTime(sliderPosition.toLong()), color = Color.White)
            Text(formatTime(duration.toLong()), color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Control Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_shuffle),
                contentDescription = "Shuffle",
                tint = if (playerState.isShuffle) Color.Cyan else Color.White,
                modifier = Modifier.clickable { onShuffle() }
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Previous",
                tint = Color.White,
                modifier = Modifier.clickable { onPrevious() }
            )

            // Play/Pause Button
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.Cyan)
                    .clickable { onPlayPauseToggle() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = if (playerState.isPlaying)
                        painterResource(id = R.drawable.ic_play)
                    else
                        painterResource(id = R.drawable.ic_pause),
                    contentDescription = if (playerState.isPlaying) "Play" else "Pause",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp),
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = "Next",
                tint = Color.White,
                modifier = Modifier.clickable { onNext() }
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_refresh),
                contentDescription = "Repeat",
                tint = if (playerState.isRepeat) Color.Cyan else Color.White,
                modifier = Modifier.clickable { onRepeat() }
            )
        }
    }
}
