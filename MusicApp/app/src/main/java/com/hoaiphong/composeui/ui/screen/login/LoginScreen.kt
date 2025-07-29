package com.hoaiphong.composeui.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.hoaiphong.composeui.data.model.UserManager

@Preview(showBackground = true)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    defaultUsername: String = "",
    defaultPassword: String = ""
) {
    val focusManager = LocalFocusManager.current

    var username by rememberSaveable { mutableStateOf(defaultUsername) }
    var password by rememberSaveable { mutableStateOf(defaultPassword) }
    var rememberMeChecked by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Login to your account",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )

        Spacer(Modifier.padding(16.dp))

        TextInput(
            value = username,
            onValueChange = { username = it },
            leadingIcon = Icons.Default.Person,
            placeholderText = "Username",
            visualTransformation = VisualTransformation.None
        )

        Spacer(Modifier.padding(8.dp))

        PasswordInput(
            password = password,
            onPasswordChange = { password = it }
        )
        Spacer(modifier = Modifier.padding(8.dp))

        RememberMeCheckbox(
            checked = rememberMeChecked,
            onCheckedChange = { rememberMeChecked = it },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 4.dp)
        )

        Spacer(modifier = Modifier.padding(16.dp))

        LoginButton(
            text = "Log in",
            onClick = {
                if (UserManager.validateLogin(username, password)) {
                    println("Đăng nhập thành công cho user: $username")
                    onLoginSuccess()
                } else {
                    println("Sai tài khoản hoặc mật khẩu")
                }
            }
        )


        //Spacer(modifier = Modifier.padding(32.dp))
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don't have an account?",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Sign Up",
                color = Color(0xFF00BCD4),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.clickable {
                    onSignUpClick()
                }
            )
        }
    }
}