package com.example.auth.graph.signup

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _eventChannel = Channel<SignUpEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private val _name = MutableStateFlow("")
    val nameText = _name.asStateFlow()

    private val _isNameValid = MutableStateFlow(false)
    val isNameValid = _isNameValid.asStateFlow()

    private val _selectedImageUri  = MutableStateFlow<Uri?>(null)
    val selectedImageUri = _selectedImageUri.asStateFlow()

    fun setName(name : String) {
        _name.value = name
    }



    internal fun registerUser() = viewModelScope.launch {
        authRepository.registerUser().onSuccess {

        }.onFailure {

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

    sealed class SignUpEvent {
        data object SignUpSuccess : SignUpEvent()
    }
}