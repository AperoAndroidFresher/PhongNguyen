package com.hoaiphong.composeui.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.ui.navigation.MyInformationScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Any) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopEnd),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { onNavigate(MyInformationScreen) }) {
                Icon(
                    imageVector = MyInformationScreen.icon,
                    contentDescription = "My Info"
                )
            }
        }

    }
}