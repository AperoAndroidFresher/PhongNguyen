package com.hoaiphong.composeui.ui.screen.signup

import androidx.lifecycle.ViewModel
import com.hoaiphong.composeui.data.model.User
import com.hoaiphong.composeui.data.model.UserManager
import com.hoaiphong.composeui.ui.screen.login.LoginIntent
import com.hoaiphong.composeui.ui.screen.signup.SignUpEffect.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()

    private val _effect = Channel<SignUpEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun dispatch(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.UsernameChanged -> {
                _state.update { it.copy(username = intent.value, usernameError = false) }
            }
            is SignUpIntent.PasswordChanged -> {
                _state.update { it.copy(password = intent.value, passwordError = false) }
            }
            is SignUpIntent.ConfirmPasswordChanged -> {
                _state.update { it.copy(confirmPassword = intent.value, confirmPasswordError = false) }
            }
            is SignUpIntent.EmailChanged -> {
                _state.update { it.copy(email = intent.value, emailError = false) }
            }
            is SignUpIntent.TogglePasswordVisibility -> {
                _state.update { it.copy(passwordVisible = !it.passwordVisible) }
            }
            is SignUpIntent.SubmitSignUp -> handleSignUp()
            is SignUpIntent.BackClicked -> CoroutineScope(Dispatchers.Main).launch {
                _effect.send(NavigateBack("", ""))
            }

        }
    }

    private fun handleSignUp() {
        val state = _state.value
        val usernameRegex = Regex("^[a-z0-9]+$")
        val passwordRegex = Regex("^[a-zA-Z0-9]+$")
        val emailRegex = Regex("^[a-z0-9._-]+@apero\\.vn$")
        val noWhitespace = Regex("^\\S+$")

        var hasError = false

        if (!state.username.matches(usernameRegex) || !state.username.matches(noWhitespace)) {
            _state.update { it.copy(usernameError = true, username = "") }
            hasError = true
        }

        if (!state.password.matches(passwordRegex) || !state.password.matches(noWhitespace)) {
            _state.update { it.copy(passwordError = true, password = "") }
            hasError = true
        }

        if (state.confirmPassword != state.password || !state.confirmPassword.matches(noWhitespace)) {
            _state.update { it.copy(confirmPasswordError = true, confirmPassword = "") }
            hasError = true
        }

        if (!state.email.matches(emailRegex) || !state.email.matches(noWhitespace)) {
            _state.update { it.copy(emailError = true, email = "") }
            hasError = true
        }

        if (!hasError) {
            UserManager.addUser(User(state.username, state.password, state.email))
            CoroutineScope(Dispatchers.Main).launch {
                _effect.send(SignUpEffect.NavigateBack(state.username, state.password))
            }
        }
    }
}
