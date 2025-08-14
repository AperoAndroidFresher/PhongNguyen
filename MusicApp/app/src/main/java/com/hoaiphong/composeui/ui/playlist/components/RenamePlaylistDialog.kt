package com.hoaiphong.composeui.ui.playlist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.R

@Composable
fun RenamePlaylistDialog(
    currentName: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf(currentName) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Text(
                stringResource(R.string.ok),
                modifier = Modifier.Companion
                    .padding(8.dp)
                    .clickable { onConfirm(text) },
                color = Color.Companion.White,
            )
        },
        dismissButton = {
            Text(
                stringResource(R.string.cancel),
                modifier = Modifier.Companion
                    .padding(8.dp)
                    .clickable { onDismiss() },
                color = Color.Companion.Gray,
            )
        },
        title = { Text(stringResource(R.string.rename_playlist), color = Color.Companion.White) },
        text = {
            TextField(
                value = text, onValueChange = { text = it }, singleLine = true
            )
        },
        containerColor = Color.Companion.DarkGray,
    )
}
