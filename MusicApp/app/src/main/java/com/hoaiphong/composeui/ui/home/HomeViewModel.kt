package com.hoaiphong.composeui.ui.home

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.hoaiphong.composeui.api.client.HomeRetrofitClient
import com.hoaiphong.composeui.api.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = mutableStateOf(HomeState())
    val uiState: State<HomeState> = _uiState

    private fun postError(message: String) {
        _uiState.value = _uiState.value.copy(
            errorMessage = message,
            hasNetworkError = true,
            isLoading = false,
        )
    }

    fun loadTop6Albums() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        HomeRetrofitClient.build().getTop6Albums()
            .enqueue(
                object : Callback<TopAlbumsResponse> {
                    override fun onResponse(call: Call<TopAlbumsResponse>, response: Response<TopAlbumsResponse>) {
                        if (response.isSuccessful) {
                            val albums = response.body()?.topalbums?.album ?: emptyList()
                            _uiState.value = _uiState.value.copy(
                                albums = albums,
                                errorMessage = "",
                                hasNetworkError = false,
                                isLoading = false,
                            )
                        } else {
                            postError("Albums Error: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<TopAlbumsResponse>, t: Throwable) {
                        postError("Albums Failure: ${t.message}")
                    }
                },
            )
    }

    fun loadTopAlbums() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        HomeRetrofitClient.build().getTopAlbums()
            .enqueue(
                object : Callback<TopAlbumsResponse> {
                    override fun onResponse(call: Call<TopAlbumsResponse>, response: Response<TopAlbumsResponse>) {
                        if (response.isSuccessful) {
                            val albums = response.body()?.topalbums?.album ?: emptyList()
                            _uiState.value = _uiState.value.copy(
                                albums = albums,
                                errorMessage = "",
                                hasNetworkError = false,
                                isLoading = false,
                            )
                        } else {
                            postError("Albums Error: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<TopAlbumsResponse>, t: Throwable) {
                        postError("Albums Failure: ${t.message}")
                    }
                },
            )
    }

    fun loadTop5Artists() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        HomeRetrofitClient.build1().getTop5Artists()
            .enqueue(
                object : Callback<TopArtistsResponse> {
                    override fun onResponse(call: Call<TopArtistsResponse>, response: Response<TopArtistsResponse>) {
                        if (response.isSuccessful) {
                            val artists = response.body()?.artists?.artist ?: emptyList()
                            _uiState.value = _uiState.value.copy(
                                artists = artists,
                                errorMessage = "",
                                hasNetworkError = false,
                                isLoading = false,
                            )
                        } else {
                            postError("Artists Error: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<TopArtistsResponse>, t: Throwable) {
                        postError("Artists Failure: ${t.message}")
                    }
                },
            )
    }

    fun loadTopArtists() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        HomeRetrofitClient.build1().getTopArtists()
            .enqueue(
                object : Callback<TopArtistsResponse> {
                    override fun onResponse(call: Call<TopArtistsResponse>, response: Response<TopArtistsResponse>) {
                        if (response.isSuccessful) {
                            val artists = response.body()?.artists?.artist ?: emptyList()
                            _uiState.value = _uiState.value.copy(
                                artists = artists,
                                errorMessage = "",
                                hasNetworkError = false,
                                isLoading = false,
                            )
                        } else {
                            postError("Artists Error: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<TopArtistsResponse>, t: Throwable) {
                        postError("Artists Failure: ${t.message}")
                    }
                },
            )
    }

    fun loadTop5Tracks() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        HomeRetrofitClient.build2().getTop5Tracks()
            .enqueue(
                object : Callback<TopTracksResponse> {
                    override fun onResponse(call: Call<TopTracksResponse>, response: Response<TopTracksResponse>) {
                        if (response.isSuccessful) {
                            val tracks = response.body()?.toptracks?.track ?: emptyList()
                            _uiState.value = _uiState.value.copy(
                                tracks = tracks,
                                errorMessage = "",
                                hasNetworkError = false,
                                isLoading = false,
                            )
                        } else {
                            postError("Tracks Error: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<TopTracksResponse>, t: Throwable) {
                        postError("Tracks Failure: ${t.message}")
                    }
                },
            )
    }

    fun loadTopTracks() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        HomeRetrofitClient.build2().getTopTracks()
            .enqueue(
                object : Callback<TopTracksResponse> {
                    override fun onResponse(call: Call<TopTracksResponse>, response: Response<TopTracksResponse>) {
                        if (response.isSuccessful) {
                            val tracks = response.body()?.toptracks?.track ?: emptyList()
                            _uiState.value = _uiState.value.copy(
                                tracks = tracks,
                                errorMessage = "",
                                hasNetworkError = false,
                                isLoading = false,
                            )
                        } else {
                            postError("Tracks Error: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<TopTracksResponse>, t: Throwable) {
                        postError("Tracks Failure: ${t.message}")
                    }
                },
            )
    }
}
