package com.hoaiphong.composeui.ui.home

import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hoaiphong.composeui.ui.home.components.Avatar
import com.hoaiphong.composeui.ui.home.components.TopAlbums
import com.hoaiphong.composeui.ui.home.components.TopArtist
import com.hoaiphong.composeui.ui.home.components.TopTrack
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.hoaiphong.composeui.ui.information.InfoViewModel
import com.hoaiphong.composeui.ui.library.components.LottieAnimationLoading
import com.hoaiphong.composeui.ui.library.components.NoInternetConnectionContent
import com.hoaiphong.composeui.ui.navigation.TopAlbumsDetail
import com.hoaiphong.composeui.ui.navigation.TopArtistsDetail
import com.hoaiphong.composeui.ui.navigation.TopTracksDetail

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Any) -> Unit = {},
    homeViewModel: HomeViewModel = viewModel(),
    infoViewModel: InfoViewModel = viewModel(),
) {
    val uiState by homeViewModel.uiState
    val state by infoViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.loadTop6Albums()
        homeViewModel.loadTop5Artists()
        homeViewModel.loadTop5Tracks()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Avatar(
                    modifier = Modifier,
                    onNavigate = onNavigate,
                    avatarUri = state.avatarUri,
                )
            }
        }
        if (uiState.isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillParentMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    LottieAnimationLoading()
                }
            }
        }
        else if (uiState.hasNetworkError) {
            item {
                NoInternetConnectionContent(
                    onRetry = {
                        homeViewModel.loadTop6Albums()
                        homeViewModel.loadTop5Artists()
                        homeViewModel.loadTop5Tracks()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        else {
            item {
                TopAlbums(
                    albums = uiState.albums,
                    modifier = Modifier.fillMaxWidth(),
                    onSeeAllAlbumClick = {
                        onNavigate(TopAlbumsDetail)
                    },
                )
            }

            item {
                TopTrack(
                    tracks = uiState.tracks,
                    modifier = Modifier.fillMaxWidth(),
                    onSeeAllTrackClick = {
                        onNavigate(TopTracksDetail)
                    },
                )
            }

            item {
                TopArtist(
                    artists = uiState.artists,
                    modifier = Modifier.fillMaxWidth(),
                    onSeeAllArtistClick = {
                        onNavigate(TopArtistsDetail)
                    },
                )
            }
        }
    }
}
