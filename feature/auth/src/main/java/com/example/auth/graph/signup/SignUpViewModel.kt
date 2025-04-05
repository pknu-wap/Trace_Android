package com.example.auth.graph.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _eventChannel = Channel<SignUpEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    internal fun onEvent(event: SignUpEvent) = viewModelScope.launch {
        _eventChannel.send(event)
    }

    internal fun signUp() = viewModelScope.launch {
        authRepository.signUp().onSuccess {

        }.onFailure {

        }
    }

    sealed class SignUpEvent {
        data object SignUpSuccess : SignUpEvent()
    }
}