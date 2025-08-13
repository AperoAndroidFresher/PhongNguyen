package com.hoaiphong.composeui.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.api.response.Artist
import com.hoaiphong.composeui.api.response.TrackImage
import com.hoaiphong.composeui.api.response.TracksObjectResponse
import com.hoaiphong.composeui.ui.home.HomeViewModel
import com.hoaiphong.composeui.ui.library.components.LottieAnimationLoading
import com.hoaiphong.composeui.ui.library.components.NoInternetConnectionContent

@Composable
fun TopTracksDetail(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    homeViewModel: HomeViewModel = viewModel(),
) {
    val uiState by homeViewModel.uiState

    LaunchedEffect(Unit) {
        homeViewModel.loadTopTracks()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .background(color = Color.Black),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .clickable { onBackClick() }
                    .size(32.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Top Tracks",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
            )
        }
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x88000000)),
                    contentAlignment = Alignment.Center,
                ) {
                    LottieAnimationLoading()
                }
            }

            uiState.hasNetworkError -> {
                NoInternetConnectionContent(
                    onRetry = { homeViewModel.loadTopAlbums() },
                )
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = modifier.fillMaxSize(),
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
                    itemsIndexed(uiState.tracks) { index, track ->
                        TracksDetailItem(track = track, color = colors[index % colors.size])
                    }
                }
            }
        }
    }
}

@Composable
fun TracksDetailItem(
    track: TracksObjectResponse,
    modifier: Modifier = Modifier,
    color: Color = Color.Transparent,
) {
    val imageUrl = track.image.lastOrNull()?.url
    val title = track.name
    val listens = track.listeners
    val artist = track.artist.name

    Box(
        modifier = modifier
            .padding(end = 8.dp)
            .width(140.dp)
            .height(160.dp)
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
                Spacer(Modifier.height(4.dp))
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
fun PreviewTopTracksDetail() {
    val fakeTracks = listOf(
        TracksObjectResponse(
            name = "Track 1",
            listeners = "12345",
            artist = Artist(name = "Artist 1"),
            image = listOf(TrackImage(url = "", size = "medium")),
        ),
        TracksObjectResponse(
            name = "Track 2",
            listeners = "67890",
            artist = Artist(name = "Artist 2"),
            image = listOf(TrackImage(url = "", size = "medium")),
        ),
    )
    TopTracksDetailPreviewContent(fakeTracks)
}

@Composable
fun TopTracksDetailPreviewContent(tracks: List<TracksObjectResponse>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        Spacer(Modifier.padding(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(32.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Top Tracks",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
            )
        }

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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            itemsIndexed(tracks) { index, track ->
                TracksDetailItem(track = track, color = colors[index % colors.size])
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTracksDetailItem() {
    TracksDetailItem(
        track = TracksObjectResponse(
            name = "Demo Track",
            listeners = "9999",
            artist = Artist(name = "Demo Artist"),
            image = listOf(TrackImage(url = "", size = "medium")),
        ),
        color = Color(0xFF6666FF),
    )
}
