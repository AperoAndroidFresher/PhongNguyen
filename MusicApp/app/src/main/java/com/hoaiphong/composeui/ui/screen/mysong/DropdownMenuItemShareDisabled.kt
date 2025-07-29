package com.hoaiphong.composeui.ui.screen.mysong

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.R

@Composable
fun DropdownMenuItemShareDisabled() {
    DropdownMenuItem(
        text = {
            Text("Share (coming soon)", color = Color.Companion.Gray)
        },
        onClick = { },
        enabled = false,
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_about),
                contentDescription = null,
                modifier = Modifier.Companion.size(18.dp),
                colorFilter = ColorFilter.Companion.tint(Color.Companion.White)
            )
        }
    )
}