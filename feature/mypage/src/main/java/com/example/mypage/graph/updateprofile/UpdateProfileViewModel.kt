package com.example.mypage.graph.updateprofile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(

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
