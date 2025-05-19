package com.example.mypage.graph.updateprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _eventChannel = Channel<UpdateProfileEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _isNameValid = MutableStateFlow(true)
    val isNameValid = _isNameValid.asStateFlow()

    private val _profileImageUrl = MutableStateFlow<String?>(null)
    val profileImage = _profileImageUrl.asStateFlow()

    init {
        getUserInfo()
    }

    private fun getUserInfo() = viewModelScope.launch {
        userRepository.loadUserInfo().onSuccess { userInfo ->
            setName(userInfo.name)
            setProfileImageUrl(userInfo.profileImageUrl)
        }
    }

    fun setName(name: String) {
        _name.value = name
        validateName()
    }

    fun setProfileImageUrl(imageUrl: String?) {
        _profileImageUrl.value = imageUrl
    }

    private fun validateName() {
        val nicknameRegex = "^.{$NAME_MIN_LENGTH,$NAME_MAX_LENGTH}$".toRegex()
        _isNameValid.value = _name.value.trim().matches(nicknameRegex)
    }

    fun updateProfile() {}


    companion object {
        private const val NAME_MIN_LENGTH = 2
        private const val NAME_MAX_LENGTH = 12
    }

    sealed class UpdateProfileEvent {
        data object NavigateBack : UpdateProfileEvent()
    }
}
