package com.hoaiphong.composeui.utils

import android.content.Context
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable


@Composable
fun rememberImagePicker(
    context: Context,
    onImagePicked: (Uri?) -> Unit
): ManagedActivityResultLauncher<String, Uri?> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = onImagePicked
    )
}