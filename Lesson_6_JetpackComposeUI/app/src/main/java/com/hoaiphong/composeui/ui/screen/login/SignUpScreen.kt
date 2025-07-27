package com.hoaiphong.composeui.ui.screen.login
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.ui.screen.myinfo.ErrText

@Preview(showBackground = true)
@Composable
fun SignUpScreen(onBackClick: () -> Unit = {}) {
    val focusManager = LocalFocusManager.current

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    var usernameError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }

    val usernameRegex = Regex("^[a-z0-9]+$")
    val passwordRegex = Regex("^[a-zA-Z0-9]+$")
    val emailRegex = Regex("^[a-z0-9._-]+@apero\\.vn$")
    val noWhitespaceRegex = Regex("^\\S+$")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back icon
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.Start)
                .clickable { onBackClick() }
                .padding(8.dp)
                .size(24.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Logo
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
            value = username,
            onValueChange = {
                username = it
                usernameError = false
            },
            leadingIcon = Icons.Default.Person,
            placeholderText = "Username",
            visualTransformation = VisualTransformation.None
        )
        if (usernameError) {
            ErrText("Invalid format")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Password
        PasswordInput(
            password = password,
            onPasswordChange = {
                password = it
                passwordError = false
            }
        )
        if (passwordError) {
            ErrText("Invalid format")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Confirm Password
        PasswordInput(
            password = confirmPassword,
            onPasswordChange = {
                confirmPassword = it
                confirmPasswordError = false
            },
            labelText = "Confirm password"
        )
        if (confirmPasswordError) {
            ErrText("Confirmation password does not match")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Email
        TextInput(
            value = email,
            onValueChange = {
                email = it
                emailError = false
            },
            leadingIcon = Icons.Default.Email,
            placeholderText = "Email",
            visualTransformation = VisualTransformation.None
        )
        if (emailError) {
            ErrText("Invalid format")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sign up button
        LoginButton(
            text = "Sign up",
            onClick = {
                var hasError = false

                if (username.isBlank() || !username.matches(usernameRegex) || !username.matches(noWhitespaceRegex)) {
                    usernameError = true
                    username = "" // clear nếu lỗi
                    hasError = true
                }

                if (password.isBlank() || !password.matches(passwordRegex) || !password.matches(noWhitespaceRegex)) {
                    passwordError = true
                    password = ""
                    hasError = true
                }

                if (confirmPassword != password || !confirmPassword.matches(noWhitespaceRegex)) {
                    confirmPasswordError = true
                    confirmPassword = ""
                    hasError = true
                }

                if (email.isBlank() || !email.matches(emailRegex) || !email.matches(noWhitespaceRegex)) {
                    emailError = true
                    email = ""
                    hasError = true
                }

                if (!hasError) {

                }
            }
        )

    }
}