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
    fun renamePlaylist(oldName: String, newName: String) {
        val index = playlists.indexOfFirst { it.name == oldName }
        if (index != -1) {
            val current = playlists[index]
            playlists[index] = current.copy(name = newName)
        }
    }
    fun removePlaylist(name: String) {
        playlists.removeIf { it.name == name }
    }
}