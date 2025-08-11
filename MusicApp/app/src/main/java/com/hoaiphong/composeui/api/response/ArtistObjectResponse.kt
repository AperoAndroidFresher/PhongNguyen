package com.hoaiphong.composeui.api.response

import com.google.gson.annotations.SerializedName

data class TopArtistsResponse(
    @SerializedName("artists")
    val artists: Artists
)

data class Artists(
    @SerializedName("artist")
    val artist: List<ArtistObjectResponse>
)

data class ArtistObjectResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: List<ArtistImage>
)

data class ArtistImage(
    @SerializedName("#text")
    val url: String,
    @SerializedName("size")
    val size: String
)
