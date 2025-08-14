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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.api.response.AlbumArtist
import com.hoaiphong.composeui.api.response.AlbumImage
import com.hoaiphong.composeui.api.response.AlbumObjectResponse
import com.hoaiphong.composeui.ui.home.HomeViewModel
import com.hoaiphong.composeui.ui.library.components.LottieAnimationLoading
import com.hoaiphong.composeui.ui.library.components.NoInternetConnectionContent

@Composable
fun TopAlbumsDetail(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    homeViewModel: HomeViewModel = viewModel()
) {
    val uiState by homeViewModel.uiState

    LaunchedEffect(Unit) {
        homeViewModel.loadTopAlbums()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .background(Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .align(Alignment.TopStart),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
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
                text = stringResource(R.string.top_albums),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
            )
        }

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x88000000))
                        .align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    LottieAnimationLoading()
                }
            }
            uiState.hasNetworkError -> {
                NoInternetConnectionContent(
                    onRetry = { homeViewModel.loadTopAlbums() },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 56.dp), // Tránh bị che header
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.albums.size) { index ->
                        AlbumsDetailItem(album = uiState.albums[index])
                    }
                }
            }
        }
    }
}



@Composable
fun AlbumsDetailItem(
    album: AlbumObjectResponse,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF1A1A1A))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.size(60.dp),
        ) {
            AsyncImage(
                model = album.image.lastOrNull()?.url.takeIf { !it.isNullOrEmpty() } ?: R.drawable.song1,
                contentDescription = album.name,
                modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = album.name,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = album.artist.name,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewTopAlbumsDetail() {
    val fakeAlbums = listOf(
        AlbumObjectResponse(
            name = "Album 1",
            artist = AlbumArtist(name = "Artist 1"),
            image = listOf(
                AlbumImage(url = "", size = "medium")
            )
        ),
        AlbumObjectResponse(
            name = "Album 2",
            artist = AlbumArtist(name = "Artist 2"),
            image = listOf(
                AlbumImage(url = "", size = "medium")
            )
        )
    )
    TopAlbumsDetailPreviewContent(fakeAlbums)
}

@Composable
fun TopAlbumsDetailPreviewContent(albums: List<AlbumObjectResponse>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
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
                text = "Top Albums",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(albums.size) { index ->
                AlbumsDetailItem(album = albums[index])
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAlbumsDetailItem() {
    AlbumsDetailItem(
        album = AlbumObjectResponse(
            name = "Demo Album",
            artist = AlbumArtist(name = "Demo Artist"),
            image = listOf(AlbumImage(url = "", size = "medium"))
        )
    )
}
