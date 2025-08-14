package com.hoaiphong.composeui.ui.navigation

import com.hoaiphong.composeui.R

sealed interface Route

data object Splash : Route
data class Login(val defaultUsername: String = "", val defaultPassword: String = "") : Route
data object SignUp : Route

sealed interface TopLevelRoute: Route {
    val iconRes: Int
    val label: String
}

data object Home : TopLevelRoute {
    override val iconRes = R.drawable.ic_home
    override val label = "Home"
}

data object MyInformationScreen : TopLevelRoute {
    override val iconRes = R.drawable.ic_settings
    override val label = ""
}

data object Library : TopLevelRoute {
    override val iconRes = R.drawable.ic_library
    override val label = "Library"
}

data object Playlist : TopLevelRoute {
    override val iconRes: Int = R.drawable.ic_playlist
    override val label = "Playlist"
}

data class PlaylistSongs(val playlistId: Long) : Route

data object PlayingSong : Route

data object TopAlbumsDetail : Route

data object TopArtistsDetail : Route

data object TopTracksDetail : Route

data object LanguageSetting : Route

data class PlaylistSortScreen(val playlistId: Long) : Route
