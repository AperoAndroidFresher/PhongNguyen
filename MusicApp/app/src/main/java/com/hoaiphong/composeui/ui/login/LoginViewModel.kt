package com.hoaiphong.composeui.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import com.hoaiphong.composeui.comon.UserSession
import com.hoaiphong.composeui.data.repository.impl.UserRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val userSession = UserSession(application)

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _effect = Channel<LoginEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun prefillCredentials(username: String, password: String) {
        _state.update {
            it.copy(username = username, password = password)
        }
    }
    private fun handleLogin() {
        val current = _state.value
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepository = UserRepositoryImpl(application)
                val user = userRepository.login(current.username, current.password)
                if (user != null) {
                    UserSession(application).saveUser(user.userName)
                    _effect.send(LoginEffect.NavigateToHome)
                } else {
                    _effect.send(LoginEffect.ShowToast("Sai tài khoản hoặc mật khẩu"))
                }
            } catch (e: Exception) {
                _effect.send(LoginEffect.ShowToast("Đăng nhập thất bại: ${e.message}"))
            }
        }
    }

    // MVI Intent xử lý
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
                _state.update { it.copy(passwordVisible = !_state.value.passwordVisible) }
            }

            is LoginIntent.SubmitLogin -> {
                handleLogin()
            }

            is LoginIntent.NavigateToSignUp -> {
                _effect.trySend(LoginEffect.NavigateToSignUp)
            }
        }
    }
}


