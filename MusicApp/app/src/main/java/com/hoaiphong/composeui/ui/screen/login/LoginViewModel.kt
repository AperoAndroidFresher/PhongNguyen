package com.hoaiphong.composeui.ui.screen.login

import androidx.lifecycle.ViewModel
import com.hoaiphong.composeui.data.model.UserManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _effect = Channel<LoginEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun dispatch(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.UsernameChanged -> {
                _state.update { it.copy(username = intent.username) }
            }
            is LoginIntent.PasswordChanged -> {
                _state.update { it.copy(password = intent.password) }
            }
            is LoginIntent.RememberMeChanged -> {
                _state.update { it.copy(rememberMe = intent.checked) }
            }
            is LoginIntent.TogglePasswordVisibility -> {
                _state.update { it.copy(passwordVisible = !it.passwordVisible) }
            }
            is LoginIntent.SubmitLogin -> {
                val current = _state.value
                if (UserManager.validateLogin(current.username, current.password)) {
                    _effect.trySend(LoginEffect.NavigateToHome)
                } else {
                    _effect.trySend(LoginEffect.ShowToast("Sai tài khoản hoặc mật khẩu"))
                }
            }
            is LoginIntent.NavigateToSignUp -> {
                _effect.trySend(LoginEffect.NavigateToSignUp)
            }
        }
    }
}

