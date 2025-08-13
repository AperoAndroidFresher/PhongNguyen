package com.hoaiphong.composeui.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
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

data object MySong : TopLevelRoute {
    override val iconRes = R.drawable.ic_library
    override val label = "Library"
}

data object Song : TopLevelRoute {
    override val iconRes: Int = R.drawable.ic_playlist
    override val label = "Playlist"
}

data class PlaylistSongs(val playlistId: Long) : Route

data object PlayingSong : Route

data object TopAlbumsDetail : Route

data object TopArtistsDetail : Route

data object TopTracksDetail : Route

data class PlaylistSortScreen(val playlistId: Long) : Route
