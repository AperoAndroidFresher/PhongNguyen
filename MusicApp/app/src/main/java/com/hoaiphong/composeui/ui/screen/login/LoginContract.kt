package com.hoaiphong.composeui.ui.screen.login

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

data class LoginState(
    val username: String = "",
    val password: String = "",
    val rememberMe: Boolean = false
)

sealed interface LoginIntent {
    data class UsernameChanged(val username: String) : LoginIntent
    data class PasswordChanged(val password: String) : LoginIntent
    data class RememberMeChanged(val checked: Boolean) : LoginIntent
    object SubmitLogin : LoginIntent
    object NavigateToSignUp : LoginIntent
}

sealed interface LoginEffect {
    object NavigateToHome : LoginEffect
    object NavigateToSignUp : LoginEffect
    data class ShowToast(val message: String) : LoginEffect
}

interface LoginViewModelContract {
    val state: StateFlow<LoginState>
    val effect: SharedFlow<LoginEffect>
    fun dispatch(intent: LoginIntent)
}