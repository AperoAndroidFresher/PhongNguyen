package com.hoaiphong.composeui.ui.screen.login

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.hoaiphong.composeui.data.model.getAllMp3File

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onSignUpClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    defaultUsername: String = "",
    defaultPassword: String = "",
    navController: NavHostController,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.prefillCredentials(defaultUsername, defaultPassword)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is LoginEffect.NavigateToHome -> onLoginSuccess()
                is LoginEffect.NavigateToSignUp -> onSignUpClick()
                is LoginEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    LaunchedEffect(savedStateHandle) {
        val username = savedStateHandle?.get<String>("username")
        val password = savedStateHandle?.get<String>("password")
        if (!username.isNullOrBlank() && !password.isNullOrBlank()) {
            viewModel.dispatch(LoginIntent.UsernameChanged(username))
            viewModel.dispatch(LoginIntent.PasswordChanged(password))
            savedStateHandle.remove<String>("username")
            savedStateHandle.remove<String>("password")
        }
    }

    LoginScreenContent(
        state = state,
        onIntent = viewModel::dispatch
    )
}
