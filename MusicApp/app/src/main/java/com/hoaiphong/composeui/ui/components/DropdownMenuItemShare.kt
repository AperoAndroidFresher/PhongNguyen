package com.hoaiphong.composeui.ui.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.R

@Composable
fun DropdownMenuItemShare() {
    val context = LocalContext.current

    DropdownMenuItem(
        text = { Text("Share", color = Color.Companion.White) },
        onClick = {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "audio/*"
            }
            context.startActivity(Intent.createChooser(intent, "Share with"))
        },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = null,
                modifier = Modifier.Companion.size(18.dp),
                colorFilter = ColorFilter.Companion.tint(Color.Companion.White),
            )
        },
    )
}
