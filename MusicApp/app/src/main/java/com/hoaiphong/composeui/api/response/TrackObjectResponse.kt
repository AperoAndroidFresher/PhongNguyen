package com.hoaiphong.composeui.api.response

import com.google.gson.annotations.SerializedName

data class TopTracksResponse(
    @SerializedName("toptracks")
    val toptracks: TopTracks
)

data class TopTracks(
    @SerializedName("track")
    val track: List<TracksObjectResponse>
)

data class TracksObjectResponse(
    @SerializedName("name")
    val name: String,

    @SerializedName("listeners")
    val listeners: String,

    @SerializedName("artist")
    val artist: Artist,

    @SerializedName("image")
    val image: List<TrackImage>
)

data class Artist(
    @SerializedName("name")
    val name: String
)

data class TrackImage(
    @SerializedName("#text")
    val url: String,
    @SerializedName("size")
    val size: String
)
