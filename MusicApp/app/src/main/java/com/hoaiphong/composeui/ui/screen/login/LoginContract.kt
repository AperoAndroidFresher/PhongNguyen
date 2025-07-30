package com.hoaiphong.composeui.ui.screen.login


data class LoginState(
    val username: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val passwordVisible: Boolean = false
)

sealed interface LoginIntent {
    data class UsernameChanged(val username: String) : LoginIntent
    data class PasswordChanged(val password: String) : LoginIntent
    data class RememberMeChanged(val checked: Boolean) : LoginIntent
    object SubmitLogin : LoginIntent
    object NavigateToSignUp : LoginIntent
    object TogglePasswordVisibility : LoginIntent
}


