package com.hoaiphong.composeui.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.api.response.ArtistObjectResponse
import com.hoaiphong.composeui.ui.home.HomeViewModel
import com.hoaiphong.composeui.ui.library.components.LottieAnimationLoading
import com.hoaiphong.composeui.ui.library.components.NoInternetConnectionContent
@Composable
fun TopArtistsDetail(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    homeViewModel: HomeViewModel = viewModel(),
) {
    val uiState by homeViewModel.uiState

    LaunchedEffect(Unit) {
        homeViewModel.loadTopArtists()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black),
    ) {
        Spacer(Modifier.padding(16.dp))
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
                text = "Top Artists",
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
                    items(uiState.artists) { artist ->
                        ArtistDetailItem(artist = artist)
                    }
                }
            }
        }
    }
}

@Composable
fun ArtistDetailItem(
    artist: ArtistObjectResponse,
    modifier: Modifier = Modifier,
) {
    val imageUrl = artist.image.lastOrNull()?.url

    Box(
        modifier = modifier
            .padding(end = 8.dp)
            .width(140.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(8.dp)),
    ) {
        AsyncImage(
            model = imageUrl.takeIf { !it.isNullOrEmpty() } ?: R.drawable.song1,
            contentDescription = artist.name,
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
                text = artist.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 12.dp, top = 8.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopArtistsDetail() {
    val fakeArtists = listOf(
        ArtistObjectResponse(
            name = "Artist 1",
            image = listOf(
                com.hoaiphong.composeui.api.response.ArtistImage(url = "", size = "medium"),
            ),
        ),
        ArtistObjectResponse(
            name = "Artist 2",
            image = listOf(
                com.hoaiphong.composeui.api.response.ArtistImage(url = "", size = "medium"),
            ),
        ),
    )
    TopArtistsDetailPreviewContent(fakeArtists)
}

@Composable
fun TopArtistsDetailPreviewContent(artists: List<ArtistObjectResponse>) {
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
                text = "Top Artists",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(artists) { artist ->
                ArtistDetailItem(artist = artist)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewArtistDetailItem() {
    ArtistDetailItem(
        artist = ArtistObjectResponse(
            name = "Demo Artist",
            image = listOf(com.hoaiphong.composeui.api.response.ArtistImage(url = "", size = "medium")),
        ),
    )
}
