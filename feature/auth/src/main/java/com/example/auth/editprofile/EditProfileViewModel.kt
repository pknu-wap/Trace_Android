package com.example.auth.editprofile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.event.EventHelper
import com.example.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    val eventHelper: EventHelper,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _eventChannel = Channel<EditProfileEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private val idToken: String = requireNotNull(savedStateHandle["idToken"])

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _isNameValid = MutableStateFlow(false)
    val isNameValid = _isNameValid.asStateFlow()

    private val _profileImage = MutableStateFlow<String?>(null)
    val profileImage = _profileImage.asStateFlow()

    fun setName(name: String) {
        _name.value = name
        validateName()
    }

    fun setProfileImage(imageUri: String?) {
        _profileImage.value = imageUri
    }

    internal fun registerUser() = viewModelScope.launch {
        authRepository.registerUser(idToken, name.value, profileImage.value).onSuccess {
            _eventChannel.send(EditProfileEvent.RegisterUserSuccess)
        }.onFailure {
            _eventChannel.send(EditProfileEvent.RegisterUserFailure)
        }
    }

    private fun validateName() {
        val nicknameRegex = "^.{$NAME_MIN_LENGTH,$NAME_MAX_LENGTH}$".toRegex()
        _isNameValid.value = _name.value.trim().matches(nicknameRegex)
    }

    companion object {
        private const val NAME_MIN_LENGTH = 2
        private const val NAME_MAX_LENGTH = 12
    }

    sealed class EditProfileEvent {
        data object RegisterUserSuccess : EditProfileEvent()
        data object RegisterUserFailure : EditProfileEvent()
    }
}