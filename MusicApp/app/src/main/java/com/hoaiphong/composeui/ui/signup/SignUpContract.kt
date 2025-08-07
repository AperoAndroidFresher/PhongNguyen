package com.hoaiphong.composeui.ui.signup


data class SignUpState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val email: String = "",
    val usernameError: Boolean = false,
    val passwordError: Boolean = false,
    val confirmPasswordError: Boolean = false,
    val emailError: Boolean = false,
    val passwordVisible: Boolean = false
)

sealed interface SignUpIntent {
    data class UsernameChanged(val value: String) : SignUpIntent
    data class PasswordChanged(val value: String) : SignUpIntent
    data class ConfirmPasswordChanged(val value: String) : SignUpIntent
    data class EmailChanged(val value: String) : SignUpIntent
    object SubmitSignUp : SignUpIntent
    object BackClicked : SignUpIntent
    object TogglePasswordVisibility : SignUpIntent
}

sealed interface SignUpEffect {
    data class ShowToast(val message: String) : SignUpEffect
    data class NavigateBack(val username: String, val password: String) : SignUpEffect
}


