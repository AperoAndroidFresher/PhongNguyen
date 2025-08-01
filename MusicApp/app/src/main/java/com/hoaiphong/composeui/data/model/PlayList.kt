package com.hoaiphong.composeui.data.model

data class Playlist(
    val name: String,
    val songs: MutableList<Song> = mutableListOf()
)

object PlaylistManager {
    private val playlists = mutableListOf<Playlist>()

    fun getPlaylists(): List<Playlist> = playlists

    fun addPlaylist(name: String): Playlist {
        val playlist = Playlist(name)
        playlists.add(playlist)
        return playlist
    }

    fun addSongToPlaylist(playlistName: String, song: Song) {
        playlists.find { it.name == playlistName }?.songs?.add(song)
    }
}