package com.hoaiphong.composeui.ui.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.hoaiphong.composeui.ui.navigation.Home
import com.hoaiphong.composeui.ui.navigation.MySong
import com.hoaiphong.composeui.ui.navigation.Song
import com.hoaiphong.composeui.ui.navigation.TopLevelRoute

@Composable
fun SharedBottom(
    selectedRoute: TopLevelRoute,
    onNavigate: (TopLevelRoute) -> Unit,
    content: @Composable (innerPadding: PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                listOf(Home, MySong, Song).forEach { route ->
                    NavigationBarItem(
                        selected = route == selectedRoute,
                        onClick = { onNavigate(route) },
                        icon = {
                            Icon(imageVector = route.icon, contentDescription = null)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}
