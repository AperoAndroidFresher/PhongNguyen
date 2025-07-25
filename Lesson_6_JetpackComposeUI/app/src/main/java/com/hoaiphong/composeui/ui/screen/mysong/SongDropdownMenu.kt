package com.hoaiphong.composeui.ui.screen.mysong

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun SongDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    Box(
        modifier = modifier.wrapContentSize(Alignment.Companion.TopStart)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            modifier = modifier
                .width(290.dp)
                .background(Color.Companion.Black.copy(alpha = 0.8f)),
            offset = DpOffset(x = -30.dp, y = 40.dp)
        ) {
            DropdownMenuItemRemove {
                onDismissRequest()
                onRemoveClick()
            }
            DropdownMenuItemShareDisabled()
        }
    }
}