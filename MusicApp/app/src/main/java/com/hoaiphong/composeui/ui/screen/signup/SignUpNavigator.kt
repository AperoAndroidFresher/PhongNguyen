package com.hoaiphong.composeui.ui.screen.signup


sealed interface SignUpEffect {
    data class ShowToast(val message: String) : SignUpEffect
    data class NavigateBack(val username: String, val password: String) : SignUpEffect
}

