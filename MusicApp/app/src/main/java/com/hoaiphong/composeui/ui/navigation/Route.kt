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

sealed interface TopLevelRoute : Route {
    val iconRes: Int
}

data object Home : TopLevelRoute {
    override val iconRes = R.drawable.ic_home
}

data object MyInformationScreen : TopLevelRoute {
    override val iconRes = R.drawable.ic_settings
}

data object MySong : TopLevelRoute {
    override val iconRes = R.drawable.ic_library
}

data object Song : TopLevelRoute {
    override val iconRes: Int = R.drawable.ic_playlist
}

data class PlaylistSongs(val playlistId: Long) : Route

data object PlayingSong : Route

data object TopAlbumsDetail : Route

data object TopArtistsDetail : Route

data object TopTracksDetail : Route
