package com.hoaiphong.composeui.ui.screen.myinfo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import java.io.File
import java.io.FileOutputStream

@Composable
fun rememberImagePickerLauncher(
    context: Context,
    onImagePicked: (String) -> Unit
): () -> Unit {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val resized = Bitmap.createScaledBitmap(bitmap, 300, 300, true)

            val filename = "avatar_${System.currentTimeMillis()}.png"
            val file = File(context.cacheDir, filename)

            val outputStream = FileOutputStream(file)
            resized.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()

            onImagePicked(file.absolutePath)
        }
    }
    return { launcher.launch("image/*") }
}