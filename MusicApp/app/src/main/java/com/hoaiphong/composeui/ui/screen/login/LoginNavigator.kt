package com.hoaiphong.composeui.ui.screen.login



sealed interface LoginEffect {
    object NavigateToHome : LoginEffect
    object NavigateToSignUp : LoginEffect
    data class ShowToast(val message: String) : LoginEffect
}
