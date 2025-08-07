package com.hoaiphong.composeui.ui.signup

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.hoaiphong.composeui.ui.signup.components.SignUpContent

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    onSignUpSuccess: (username: String, password: String) -> Unit,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SignUpEffect.NavigateBack -> {
                    onSignUpSuccess(effect.username, effect.password)
                }

                is SignUpEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    SignUpContent(
        state = state,
        onIntent = viewModel::dispatch,
    )
}
