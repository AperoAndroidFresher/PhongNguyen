package com.hoaiphong.composeui.ui.playlistsong.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.R

@Composable
fun DropdownMenuItemAdd(onClick: () -> Unit) {
    DropdownMenuItem(
        text = {
            Text(
                "Add to playlist",
                fontWeight = FontWeight.Companion.Bold,
                color = Color.Companion.White,
            )
        },
        onClick = onClick,
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_add_to_playlist),
                contentDescription = null,
                modifier = Modifier.Companion.size(18.dp),
                colorFilter = ColorFilter.Companion.tint(Color.Companion.White),
            )
        },
    )
}
