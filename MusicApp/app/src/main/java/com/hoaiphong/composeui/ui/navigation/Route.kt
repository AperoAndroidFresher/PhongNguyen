package com.hoaiphong.composeui.ui.navigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector


sealed interface Route

data object Splash : Route
data class Login(val defaultUsername: String = "", val defaultPassword: String = "") : Route
data object SignUp : Route

sealed interface TopLevelRoute : Route {
    val icon: ImageVector
}

data object Home : TopLevelRoute {
    override val icon = Icons.Default.Home
}

data object MyInformationScreen : TopLevelRoute {
    override val icon = Icons.Default.Settings
}

data object MySong : TopLevelRoute {
    override val icon = Icons.AutoMirrored.Filled.List
}

data object Song : TopLevelRoute {
    override val icon = Icons.AutoMirrored.Filled.List
}

data class PlaylistSongs(val playlistId: Long) : Route


