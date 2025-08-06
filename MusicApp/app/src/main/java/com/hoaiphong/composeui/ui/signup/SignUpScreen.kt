package com.hoaiphong.composeui.ui.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.ui.login.LoginButton
import com.hoaiphong.composeui.ui.login.PasswordInput
import com.hoaiphong.composeui.ui.login.TextInput
import com.hoaiphong.composeui.ui.myinfo.components.ErrText

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    onSignUpSuccess: (username: String, password: String) -> Unit,
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back button
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.Start)
                .clickable { viewModel.dispatch(SignUpIntent.BackClicked) }
                .padding(8.dp)
                .size(24.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(250.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sign up",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Username
        TextInput(
            value = state.username,
            onValueChange = { viewModel.dispatch(SignUpIntent.UsernameChanged(it)) },
            leadingIcon = Icons.Default.Person,
            placeholderText = "Username",
            visualTransformation = VisualTransformation.None
        )
        if (state.usernameError) ErrText("Invalid format")

        Spacer(modifier = Modifier.height(12.dp))

        // Password
        PasswordInput(
            password = state.password,
            onPasswordChange = { viewModel.dispatch(SignUpIntent.PasswordChanged(it)) },
            passwordVisible = state.passwordVisible,
            onPasswordVisibilityChange = { viewModel.dispatch(SignUpIntent.TogglePasswordVisibility) }
        )
        if (state.passwordError) ErrText("Invalid format")

        Spacer(modifier = Modifier.height(12.dp))

        // Confirm Password
        PasswordInput(
            password = state.confirmPassword,
            onPasswordChange = { viewModel.dispatch(SignUpIntent.ConfirmPasswordChanged(it)) },
            labelText = "Confirm password",
            passwordVisible = state.passwordVisible,
            onPasswordVisibilityChange = { viewModel.dispatch(SignUpIntent.TogglePasswordVisibility) }
        )
        if (state.confirmPasswordError) ErrText("Confirmation password is not match")

        Spacer(modifier = Modifier.height(12.dp))

        // Email
        TextInput(
            value = state.email,
            onValueChange = { viewModel.dispatch(SignUpIntent.EmailChanged(it)) },
            leadingIcon = Icons.Default.Email,
            placeholderText = "Email",
            visualTransformation = VisualTransformation.None
        )
        if (state.emailError) ErrText("Email không hợp lệ hoặc không phải @apero.vn")

        Spacer(modifier = Modifier.height(24.dp))

        // Submit button
        LoginButton(
            text = "Sign Up",
            onClick = { viewModel.dispatch(SignUpIntent.SubmitSignUp) }
        )
    }
}
