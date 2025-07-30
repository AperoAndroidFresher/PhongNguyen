package com.hoaiphong.composeui.ui.screen.myinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyInfoViewModel : ViewModel() {
    private val _state = MutableStateFlow(MyInfoState())
    val state: StateFlow<MyInfoState> = _state

    private val _effect = MutableSharedFlow<MyInfoEffect>()
    val effect: SharedFlow<MyInfoEffect> = _effect

    fun dispatch(intent: MyInfoIntent) {
        when (intent) {
            is MyInfoIntent.NameChanged -> _state.update { it.copy(name = intent.value, isNameValid = true) }
            is MyInfoIntent.PhoneChanged -> _state.update { it.copy(phone = intent.value, isPhoneValid = true) }
            is MyInfoIntent.UniversityChanged -> _state.update { it.copy(university = intent.value, isUniversityValid = true) }
            is MyInfoIntent.DescriptionChanged -> _state.update { it.copy(description = intent.value) }
            is MyInfoIntent.ToggleTheme -> _state.update { it.copy(isDarkMode = !it.isDarkMode) }
            is MyInfoIntent.AvatarChanged -> _state.update { it.copy(avatarUri = intent.uri) }
            is MyInfoIntent.ToggleEditing ->    _state.update { it.copy(isEditing = intent.editing) }
            MyInfoIntent.Submit -> {
                val current = _state.value
                val isNameValid = current.name.matches(Regex("^[a-zA-ZÀ-ỹ\\s]*$")) && current.name.isNotBlank()
                val isPhoneValid = current.phone.matches(Regex("^\\d{0,15}$")) && current.phone.isNotBlank()
                val isUniversityValid = current.university.matches(Regex("^[a-zA-ZÀ-ỹ\\s]*$")) && current.university.isNotBlank()

                if (isNameValid && isPhoneValid && isUniversityValid) {
                    _state.update {
                        it.copy(
                            isNameValid = true,
                            isPhoneValid = true,
                            isUniversityValid = true,
                            isEditing = false,
                            showDialog = true
                        )
                    }
                    viewModelScope.launch {
                        _effect.emit(MyInfoEffect.ShowSuccessDialog)
                    }
                } else {
                    _state.update {
                        it.copy(
                            isNameValid = isNameValid,
                            isPhoneValid = isPhoneValid,
                            isUniversityValid = isUniversityValid
                        )
                    }
                }
            }


        }
    }

    fun dismissDialog() {
        _state.update { it.copy(showDialog = false) }
    }
}