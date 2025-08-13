package com.hoaiphong.composeui.ui.home.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.api.response.Artist
import com.hoaiphong.composeui.api.response.TrackImage
import com.hoaiphong.composeui.api.response.TracksObjectResponse

@Composable
fun TopTrack(
    tracks: List<TracksObjectResponse>,
    modifier: Modifier = Modifier,
    onSeeAllTrackClick: () -> Unit = {},
) {
    val colors = listOf(
        Color(0xFFFF6666),
        Color(0xFFFFFF99),
        Color(0xFF6666FF),
        Color(0xFF00FF00),
        Color(0xFF00FFFF),
        Color(0xFF66FFFF),
        Color(0xFFFF0000),
        Color(0xFFFF99FF),
    )

    fun getItemColor(index: Int): Color {
        return colors[index % colors.size]
    }
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Top Track",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
            )
            Text(
                text = "See all",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF00C2CB),
                modifier = Modifier.clickable { onSeeAllTrackClick() },
            )
        }
        LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
            items(tracks.size) { index ->
                val track = tracks[index]
                TrackItem(
                    title = track.name,
                    listens = track.listeners.toString(),
                    artist = track.artist.name,
                    imageUrl = track.image.lastOrNull()?.url,
                    color = getItemColor(index),
                )
            }
        }
    }
}

@Composable
fun TrackItem(
    modifier: Modifier = Modifier,
    title: String = "Mixigaming",
    listens: String = "21323123",
    artist: String = "Mixi",
    imageUrl: String?,
    color: Color = Color.Transparent,
) {
    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .width(140.dp)
            .height(140.dp)
            .clip(RoundedCornerShape(8.dp)),
    ) {
        AsyncImage(
            model = imageUrl.takeIf { !it.isNullOrEmpty() } ?: R.drawable.song1,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(8.dp)),
            placeholder = null,
            error = null,
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.4f)),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 8.dp),
            )

            // Thông tin ở dưới
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = 16.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_listener),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp),
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = listens,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                    )
                }
                Spacer(Modifier.padding(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_artist),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp),
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = artist,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(color)
                .align(Alignment.BottomCenter),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun TopTrackPreview() {
    val sampleTracks = listOf(
        TracksObjectResponse(
            name = "Espresso",
            listeners = "972846",
            artist = Artist(name = "Sabrina Carpenter"),
            image = listOf(
                TrackImage(url = "https://example.com/image1.jpg", size = "large"),
                TrackImage(url = "https://example.com/image1_small.jpg", size = "small"),
            ),
        ),
        TracksObjectResponse(
            name = "Chill Mix",
            listeners = "840000",
            artist = Artist(name = "Olivia Rodrigo"),
            image = listOf(
                TrackImage(url = "https://example.com/image2.jpg", size = "large"),
                TrackImage(url = "https://example.com/image2_small.jpg", size = "small"),
            ),
        ),
        TracksObjectResponse(
            name = "Summer Vibes",
            listeners = "670000",
            artist = Artist(name = "Dua Lipa"),
            image = listOf(
                TrackImage(url = "https://example.com/image3.jpg", size = "large"),
                TrackImage(url = "https://example.com/image3_small.jpg", size = "small"),
            ),
        ),
    )
    TopTrack(tracks = sampleTracks)
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun TrackItemPreview() {
    TrackItem(
        title = "Espresso",
        listens = "972846",
        artist = "Sabrina Carpenter",
        imageUrl = "R.drawable.song1",
    )
}
