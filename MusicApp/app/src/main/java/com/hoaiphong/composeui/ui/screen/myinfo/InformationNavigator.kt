package com.hoaiphong.composeui.ui.screen.myinfo

sealed interface MyInfoEffect {
    object ShowSuccessDialog : MyInfoEffect
    data class ShowToast(val message: String) : MyInfoEffect
}