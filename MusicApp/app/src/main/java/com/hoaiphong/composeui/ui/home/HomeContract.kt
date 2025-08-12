package com.hoaiphong.composeui.ui.home

import com.hoaiphong.composeui.api.response.AlbumObjectResponse
import com.hoaiphong.composeui.api.response.ArtistObjectResponse
import com.hoaiphong.composeui.api.response.TracksObjectResponse

data class HomeState(
    val albums: List<AlbumObjectResponse> = emptyList(),
    val artists: List<ArtistObjectResponse> = emptyList(),
    val tracks: List<TracksObjectResponse> = emptyList(),
    val errorMessage: String = "",
    val hasNetworkError: Boolean = false,
    val isLoading: Boolean = false,
)
