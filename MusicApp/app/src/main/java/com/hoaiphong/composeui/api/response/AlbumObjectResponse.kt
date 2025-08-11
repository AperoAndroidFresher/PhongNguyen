package com.hoaiphong.composeui.api.response

import com.google.gson.annotations.SerializedName

data class TopAlbumsResponse(
    @SerializedName("topalbums")
    val topalbums: TopAlbums
)

data class TopAlbums(
    @SerializedName("album")
    val album: List<AlbumObjectResponse>
)

data class AlbumObjectResponse(
    @SerializedName("name")
    val name: String,

    @SerializedName("artist")
    val artist: AlbumArtist,

    @SerializedName("image")
    val image: List<AlbumImage>
)

data class AlbumArtist(
    @SerializedName("name")
    val name: String
)
data class AlbumImage(
    @SerializedName("#text")
    val url: String,
    @SerializedName("size")
    val size: String
)
