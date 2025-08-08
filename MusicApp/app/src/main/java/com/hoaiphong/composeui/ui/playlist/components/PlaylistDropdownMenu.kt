package com.hoaiphong.composeui.ui.playlist.components

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
import com.hoaiphong.composeui.ui.components.DropdownMenuItemRemove

@Composable
fun PlaylistDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onRemoveClick: () -> Unit,
    onRenameClick: () -> Unit,
    modifier: Modifier = Modifier
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
            offset = DpOffset(x = 50.dp, y = 200.dp)
        ) {
            DropdownMenuItemRemove {
                onDismissRequest()
                onRemoveClick()
            }
            DropdownMenuItemRename(onClick = {
                onDismissRequest()
                onRenameClick()
            })
        }
    }
}
