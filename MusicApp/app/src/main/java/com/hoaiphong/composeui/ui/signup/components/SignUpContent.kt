package com.hoaiphong.composeui.ui.signup.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.ui.components.ErrText
import com.hoaiphong.composeui.ui.components.AuthButton
import com.hoaiphong.composeui.ui.components.PasswordInput
import com.hoaiphong.composeui.ui.components.TextInput
import com.hoaiphong.composeui.ui.signup.SignUpIntent
import com.hoaiphong.composeui.ui.signup.SignUpState

@Composable
fun SignUpContent(
    state: SignUpState,
    onIntent: (SignUpIntent) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.Start)
                .clickable { onIntent(SignUpIntent.BackClicked) }
                .padding(8.dp)
                .size(24.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(250.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sign up",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextInput(
            value = state.username,
            onValueChange = { onIntent(SignUpIntent.UsernameChanged(it)) },
            leadingIcon = Icons.Default.Person,
            placeholderText = "Username",
            visualTransformation = VisualTransformation.None,
        )
        if (state.usernameError) ErrText("Invalid format")

        Spacer(modifier = Modifier.height(12.dp))

        PasswordInput(
            password = state.password,
            onPasswordChange = { onIntent(SignUpIntent.PasswordChanged(it)) },
            passwordVisible = state.passwordVisible,
            onPasswordVisibilityChange = { onIntent(SignUpIntent.TogglePasswordVisibility) },
        )
        if (state.passwordError) ErrText("Invalid format")

        Spacer(modifier = Modifier.height(12.dp))

        PasswordInput(
            password = state.confirmPassword,
            onPasswordChange = { onIntent(SignUpIntent.ConfirmPasswordChanged(it)) },
            labelText = "Confirm password",
            passwordVisible = state.passwordVisible,
            onPasswordVisibilityChange = { onIntent(SignUpIntent.TogglePasswordVisibility) },
        )
        if (state.confirmPasswordError) ErrText("Confirmation password is not match")

        Spacer(modifier = Modifier.height(12.dp))

        TextInput(
            value = state.email,
            onValueChange = { onIntent(SignUpIntent.EmailChanged(it)) },
            leadingIcon = Icons.Default.Email,
            placeholderText = "Email",
            visualTransformation = VisualTransformation.None,
        )
        if (state.emailError) ErrText("Email không hợp lệ hoặc không phải @apero.vn")

        Spacer(modifier = Modifier.height(24.dp))

        AuthButton(
            text = "Sign Up",
            onClick = { onIntent(SignUpIntent.SubmitSignUp) },
        )
    }
}
