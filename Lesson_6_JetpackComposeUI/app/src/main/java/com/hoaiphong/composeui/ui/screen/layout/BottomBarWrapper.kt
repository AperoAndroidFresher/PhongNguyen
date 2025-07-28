package com.hoaiphong.composeui.ui.screen.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hoaiphong.composeui.ui.navigation.Home
import com.hoaiphong.composeui.ui.navigation.MySong
import com.hoaiphong.composeui.ui.navigation.Song
import com.hoaiphong.composeui.ui.navigation.TopLevelRoute

@Composable
fun SharedBottom(
    selectedRoute: TopLevelRoute,
    onNavigate: (TopLevelRoute) -> Unit,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        content()

        NavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
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
}