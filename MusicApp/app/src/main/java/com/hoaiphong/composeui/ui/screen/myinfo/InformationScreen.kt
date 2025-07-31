package com.hoaiphong.composeui.ui.screen.myinfo

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hoaiphong.composeui.ui.theme.darkMode
import com.hoaiphong.composeui.ui.theme.lightMode
import kotlinx.coroutines.delay
@Composable
fun InformationScreen(
    viewModel: MyInfoViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    val launcher = rememberImagePicker(context) { uri ->
        viewModel.dispatch(MyInfoIntent.AvatarChanged(uri))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MyInfoEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is MyInfoEffect.ShowSuccessDialog -> {
                }
            }
        }
    }

    LaunchedEffect(state.showDialog) {
        if (state.showDialog) {
            delay(2000)
            viewModel.dismissDialog()
        }
    }

    MaterialTheme(
        colorScheme = if (state.isDarkMode) darkMode.color else lightMode.color,
        typography = if (state.isDarkMode) darkMode.typography else lightMode.typography,
        shapes = if (state.isDarkMode) darkMode.shapes else lightMode.shapes
    ) {
        InformationScreenContent(
            state = state,
            onIntent = viewModel::dispatch,
            onPickImage = { launcher.launch("image/*") }
        )

        if (state.showDialog) {
            Dialog(
                onDismissRequest = { viewModel.dismissDialog() },
                properties = DialogProperties(
                    dismissOnClickOutside = false,
                    usePlatformDefaultWidth = false
                )
            ) {
                AnimatedVisibility(
                    visible = state.showDialog,
                    enter = fadeIn() + scaleIn(initialScale = 0.8f),
                    exit = fadeOut() + scaleOut(targetScale = 0.8f)
                ) {
                    SuccessPopupContent()
                }
            }
        }
    }
}
