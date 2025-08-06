package com.hoaiphong.composeui.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.R

@Composable
fun LoginScreenContent(
    state: LoginState,
    onIntent: (LoginIntent) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures { focusManager.clearFocus() }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(250.dp)
        )

        Text(
            text = "Login to your account",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        Spacer(Modifier.height(16.dp))

        TextInput(
            value = state.username,
            onValueChange = { onIntent(LoginIntent.UsernameChanged(it)) }
        )
        Spacer(Modifier.height(8.dp))

        PasswordInput(
            password = state.password,
            onPasswordChange = { onIntent(LoginIntent.PasswordChanged(it)) },
            passwordVisible = state.passwordVisible,
            onPasswordVisibilityChange = { onIntent(LoginIntent.TogglePasswordVisibility) }
        )
        Spacer(Modifier.height(8.dp))

        RememberMeCheckbox(
            checked = state.rememberMe,
            onCheckedChange = { onIntent(LoginIntent.RememberMeChanged(it)) },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 4.dp)
        )
        Spacer(Modifier.height(16.dp))

        LoginButton(
            text = "Log in",
            onClick = { onIntent(LoginIntent.SubmitLogin) }
        )

        Spacer(Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Don't have an account?", color = Color.White)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "Sign Up",
                color = Color(0xFF00BCD4),
                modifier = Modifier.clickable {
                    onIntent(LoginIntent.NavigateToSignUp)
                }
            )
        }
    }
}
