package com.hoaiphong.composeui.data.model

data class Playlist(
    val name: String,
    val songs: MutableList<Song> = mutableListOf()
)

object PlaylistManager {
    private val playlists = mutableListOf<Playlist>()

    fun getPlaylists(): List<Playlist> = playlists

    fun getPlaylistByName(name: String): Playlist? {
        return playlists.find { it.name == name }
    }
    fun addPlaylist(name: String): Playlist? {
        if (playlists.any { it.name.equals(name, ignoreCase = true) }) {
            return null
        }
        val playlist = Playlist(name)
        playlists.add(playlist)
        return playlist
    }

    fun addSongToPlaylist(playlistName: String, song: Song) {
        playlists.find { it.name == playlistName }?.let { playlist ->
            println("Adding song ${song.name} to playlist $playlistName")
            if (!playlist.songs.contains(song)) {
                playlist.songs.add(song)
            }
        }
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