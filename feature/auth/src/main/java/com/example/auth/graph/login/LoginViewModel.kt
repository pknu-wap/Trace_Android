package com.example.auth.graph.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _eventChannel = Channel<LoginEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    internal fun onEvent(event : LoginEvent) = viewModelScope.launch {
        _eventChannel.send(event)
    }

    internal fun loginKakao(idToken: String) = viewModelScope.launch {
        authRepository.loginKakao(idToken).onSuccess {

        }.onFailure {

        }
    }

    sealed class LoginEvent {
        data object NavigateEditProfile : LoginEvent()
        data object NavigateToHome : LoginEvent()
        data object loginKakao : LoginEvent()
    }
}