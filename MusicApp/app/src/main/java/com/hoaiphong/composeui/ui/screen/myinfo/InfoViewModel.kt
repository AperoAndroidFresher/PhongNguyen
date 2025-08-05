package com.hoaiphong.composeui.ui.screen.myinfo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.hoaiphong.composeui.db.repository.UserRepositoryImpl
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import com.hoaiphong.composeui.data.model.UserSession

class InfoViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(MyInfoState())
    val state: StateFlow<MyInfoState> = _state

    private val _effect = MutableSharedFlow<MyInfoEffect>()
    val effect: SharedFlow<MyInfoEffect> = _effect

    init {
        // Load user data from DB when ViewModel starts
        viewModelScope.launch {
            val username = UserSession.username
            if (!username.isNullOrBlank()) {
                try {
                    val userRepository = UserRepositoryImpl(application)
                    val user = userRepository.findUserByUsername(username)
                    _state.update {
                        it.copy(
                            name = user.fullName.orEmpty(),
                            phone = user.phoneNumber.orEmpty(),
                            university = user.universityName.orEmpty(),
                            description = user.description.orEmpty(),
                            avatarUri = user.imgUrl.takeIf { it.isNotBlank() }?.toUri()
                        )
                    }
                    Log.d("DCM", "me: $user")
                } catch (e: Exception) {
                    _effect.emit(MyInfoEffect.ShowToast("Không thể tải dữ liệu người dùng"))
                }
            } else {
                _effect.emit(MyInfoEffect.ShowToast("Không tìm thấy tài khoản đã đăng nhập"))
            }
        }
    }

    fun dispatch(intent: MyInfoIntent) {
        when (intent) {
            is MyInfoIntent.NameChanged -> _state.update {
                it.copy(
                    name = intent.value,
                    isNameValid = true
                )
            }

            is MyInfoIntent.PhoneChanged -> _state.update {
                it.copy(
                    phone = intent.value,
                    isPhoneValid = true
                )
            }

            is MyInfoIntent.UniversityChanged -> _state.update {
                it.copy(
                    university = intent.value,
                    isUniversityValid = true
                )
            }

            is MyInfoIntent.DescriptionChanged -> _state.update { it.copy(description = intent.value) }
            is MyInfoIntent.ToggleTheme -> _state.update { it.copy(isDarkMode = !it.isDarkMode) }
            is MyInfoIntent.AvatarChanged -> _state.update { it.copy(avatarUri = intent.uri) }
            is MyInfoIntent.ToggleEditing -> _state.update { it.copy(isEditing = intent.editing) }
            MyInfoIntent.Submit -> {
                val current = _state.value
                val isNameValid =
                    current.name.matches(Regex("^[a-zA-ZÀ-ỹ\\s]*$")) && current.name.isNotBlank()
                val isPhoneValid =
                    current.phone.matches(Regex("^\\d{0,15}$")) && current.phone.isNotBlank()
                val isUniversityValid =
                    current.university.matches(Regex("^[a-zA-ZÀ-ỹ\\s]*$")) && current.university.isNotBlank()

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
                        try {
                            val savedUsername = UserSession.username

                            if (savedUsername.isNullOrBlank()) {
                                _effect.emit(MyInfoEffect.ShowToast("Không tìm thấy tài khoản đã đăng nhập"))
                                return@launch
                            }

                            val userRepository = UserRepositoryImpl(application)
                            userRepository.updateUser(
                                username = savedUsername,
                                fullName = current.name,
                                phongNumber = current.phone,
                                universityName = current.university,
                                description = current.description,
                                imgUrl = current.avatarUri?.toString() ?: ""
                            )

                            _effect.emit(MyInfoEffect.ShowSuccessDialog)
                            _effect.emit(MyInfoEffect.ShowToast("Cập nhật thông tin thành công"))
                        } catch (e: Exception) {
                            _effect.emit(MyInfoEffect.ShowToast("Cập nhật thất bại: ${e.message}"))
                        }
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