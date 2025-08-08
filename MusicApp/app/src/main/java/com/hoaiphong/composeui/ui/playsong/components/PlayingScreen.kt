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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.utils.formatTime

@Preview(showBackground = true)
@Composable
fun PlayingScreen() {
    val duration = 163f 
    var currentTime by remember { mutableFloatStateOf(127f) } 
    var isPlaying by remember { mutableStateOf(false) }

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
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            Text("Now Playing", color = Color.White, fontWeight = FontWeight.Bold)
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Album Art
        Image(
            painter = painterResource(id = R.drawable.song1),
            contentDescription = "Album Art",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp)),
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Song Title & Artist
        Text("grainy days", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("moody.", color = Color.Gray, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Progress Slider
        Slider(
            value = currentTime,
            onValueChange = { currentTime = it },
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
            Text(formatTime(currentTime.toLong()), color = Color.White)
            Text(formatTime(duration.toLong()), color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Control Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(Icons.Default.Close, contentDescription = "Shuffle", tint = Color.White)
            Icon(Icons.Default.PlayArrow, contentDescription = "Previous", tint = Color.White)

            // Play/Pause Button
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.Cyan)
                    .clickable { isPlaying = !isPlaying },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Close else Icons.Default.PlayArrow,
                    contentDescription = "Play/Pause",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp),
                )
            }

            Icon(Icons.Default.PlayArrow, contentDescription = "Next", tint = Color.White)
            Icon(Icons.Default.Close, contentDescription = "Repeat", tint = Color.White)
        }
    }
}
