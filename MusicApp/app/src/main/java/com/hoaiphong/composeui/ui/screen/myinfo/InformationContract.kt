package com.hoaiphong.composeui.ui.screen.myinfo

import android.net.Uri

data class MyInfoState(
    val name: String = "",
    val phone: String = "",
    val university: String = "",
    val description: String = "",
    val isEditing: Boolean = false,
    val showDialog: Boolean = false,
    val isDarkMode: Boolean = true,
    val avatarUri: Uri? = null,
    val isNameValid: Boolean = true,
    val isPhoneValid: Boolean = true,
    val isUniversityValid: Boolean = true
)

sealed interface MyInfoIntent {
    data class NameChanged(val value: String) : MyInfoIntent
    data class PhoneChanged(val value: String) : MyInfoIntent
    data class UniversityChanged(val value: String) : MyInfoIntent
    data class DescriptionChanged(val value: String) : MyInfoIntent

    object Submit : MyInfoIntent
    object ToggleTheme : MyInfoIntent

    data class ToggleEditing(val editing: Boolean) : MyInfoIntent
    data class AvatarChanged(val uri: Uri?) : MyInfoIntent


}